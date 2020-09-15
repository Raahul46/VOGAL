# -*- coding: utf-8 -*-
"""
Created on Mon Aug 26 23:29:06 2019

@author: raahul46
"""
#REQUIREMENTS
import numpy
import librosa
import librosa.display
import numpy as np
import matplotlib.pyplot as plt
import pandas as pd
import tensorflow as tf
from matplotlib.pyplot import specgram
import keras
from keras.preprocessing import sequence
from keras.models import Sequential
from keras.layers import Dense, Embedding
from keras.layers import LSTM
from keras.preprocessing.text import Tokenizer
from keras.preprocessing.sequence import pad_sequences
from keras.utils import to_categorical
from keras.layers import Input, Flatten, Dropout, Activation
from keras.layers import Conv1D, MaxPooling1D, AveragePooling1D
from keras.models import Model
from keras.callbacks import ModelCheckpoint
from sklearn.metrics import confusion_matrix
from keras import regularizers
import os
import glob 
import scipy.io.wavfile
import sys
from keras.utils import np_utils
from sklearn.preprocessing import LabelEncoder
from sklearn.utils import shuffle
import pyrebase
from keras.models import model_from_json
from pydub import AudioSegment
import random 
import wave
import contextlib
import msvcrt
import json
from sklearn.utils import shuffle
from keras.utils import np_utils
from sklearn.preprocessing import LabelEncoder       

mylist= os.listdir('RawData')
#type(mylist)
#print(mylist[1400])
#print(mylist[400][6:-16])
data, sampling_rate = librosa.load('RawData/03-01-01-01-01-01-01.wav')
#plt.figure(figsize=(15, 5))
#librosa.display.waveplot(data, sr=sampling_rate)
sr,x = scipy.io.wavfile.read('RawData/03-01-01-01-01-02-23.wav')
## Parameters: 10ms step, 30ms window
nstep = int(sr * 0.01)
nwin  = int(sr * 0.03)
nfft = nwin
window = np.hamming(nwin)
nn = range(nwin, len(x), nstep)
X = np.zeros( (len(nn), nfft//2) )
for i,n in enumerate(nn):
    xseg = x[n-nwin:n]
    z = np.fft.fft(window * xseg, nfft)
    X[i,:] = np.log(np.abs(z[:nfft//2]))

#LABELLING DATA
feeling_list=[]
for item in mylist:
    if item[6:-16]=='02' and int(item[18:-4])%2==0:
        feeling_list.append('female_calm')
    elif item[6:-16]=='02' and int(item[18:-4])%2==1:
        feeling_list.append('male_calm')
    elif item[6:-16]=='03' and int(item[18:-4])%2==0:
        feeling_list.append('female_happy')
    elif item[6:-16]=='03' and int(item[18:-4])%2==1:
        feeling_list.append('male_happy')
    elif item[6:-16]=='04' and int(item[18:-4])%2==0:
        feeling_list.append('female_sad')
    elif item[6:-16]=='04' and int(item[18:-4])%2==1:
        feeling_list.append('male_sad')
    elif item[6:-16]=='05' and int(item[18:-4])%2==0:
        feeling_list.append('female_angry')
    elif item[6:-16]=='05' and int(item[18:-4])%2==1:
        feeling_list.append('male_angry')
    elif item[6:-16]=='06' and int(item[18:-4])%2==0:
        feeling_list.append('female_fearful')
    elif item[6:-16]=='06' and int(item[18:-4])%2==1:
        feeling_list.append('male_fearful')
    elif item[:1]=='a':
        feeling_list.append('male_angry')
    elif item[:1]=='f':
        feeling_list.append('male_fearful')
    elif item[:1]=='h':
        feeling_list.append('male_happy')
    #elif item[:1]=='n':
        #feeling_list.append('neutral')
    elif item[:2]=='sa':
        feeling_list.append('male_sad')


labels = pd.DataFrame(feeling_list)
labels[:10]

df = pd.DataFrame(columns=['feature'])
bookmark=0
for index,y in enumerate(mylist):
    if mylist[index][6:-16]!='01' and mylist[index][6:-16]!='07' and mylist[index][6:-16]!='08' and mylist[index][:2]!='su' and mylist[index][:1]!='n' and mylist[index][:1]!='d':
        #FEATURE ENGINEERING
        X, sample_rate = librosa.load('RawData/'+y, res_type='kaiser_fast',duration=2.5,sr=22050*2,offset=0.5)
        sample_rate = np.array(sample_rate)
        mfccs = np.mean(librosa.feature.mfcc(y=X,sr=sample_rate,n_mfcc=13), axis=0)
        feature = mfccs
        df.loc[bookmark] = [feature]
        bookmark=bookmark+1


df[:5]
df3 = pd.DataFrame(df['feature'].values.tolist())
newdf = pd.concat([df3,labels], axis=1)
rnewdf = newdf.rename(index=str, columns={"0": "label"})
rnewdf[:5]
rnewdf = shuffle(newdf)
rnewdf[:10]
rnewdf=rnewdf.fillna(0)
newdf1 = np.random.rand(len(rnewdf)) < 0.8

#SPLIT DATA INTO TRAIN AND TEST
train = rnewdf[newdf1]
test = rnewdf[~newdf1]
#train[250:260]
trainfeatures = train.iloc[:, :-1]
trainlabel = train.iloc[:, -1:]
testfeatures = test.iloc[:, :-1]
testlabel = test.iloc[:, -1:]

#NUMPY ARRAY 
X_train = np.array(trainfeatures)
y_train = np.array(trainlabel)
X_test = np.array(testfeatures)
y_test = np.array(testlabel)

#ENCODING 
lb = LabelEncoder()
y_train = np_utils.to_categorical(lb.fit_transform(y_train))
y_test = np_utils.to_categorical(lb.fit_transform(y_test))
y_train
X_train.shape

#DIMENSION EXPANSION TO FIT INTO ARCHITECTURE
x_traincnn =np.expand_dims(X_train, axis=2)
x_testcnn= np.expand_dims(X_test, axis=2)

#MODEL ARCHITECTURE

model = Sequential()
model.add(Conv1D(256, 5,padding='same',input_shape=(216,1)))
model.add(Activation('relu'))
model.add(Conv1D(128, 5,padding='same'))
model.add(Activation('relu'))
model.add(Dropout(0.1))
model.add(MaxPooling1D(pool_size=(8)))
model.add(Conv1D(128, 5,padding='same',))
model.add(Activation('relu'))
model.add(Conv1D(128, 5,padding='same',))
model.add(Activation('relu'))
model.add(Flatten())
model.add(Dense(10))
model.add(Activation('softmax'))
opt = keras.optimizers.rmsprop(lr=0.00001, decay=1e-6)
model.summary()
model.compile(loss='categorical_crossentropy', optimizer=opt,metrics=['accuracy'])

#loading json and creating model
from keras.models import model_from_json
json_file = open('model.json', 'r')
loaded_model_json = json_file.read()
json_file.close()
loaded_model = model_from_json(loaded_model_json)

#load weights into new model
loaded_model.load_weights("saved_models/Emotion_Voice_Detection_Model.h5")
print("Loaded model from disk")
 
#evaluate loaded model on test data
loaded_model.compile(loss='categorical_crossentropy', optimizer=opt, metrics=['accuracy'])



#############################################################################################

#REPORT GENERATION AND DATABASE CONNECTION
import pyrebase
from spellchecker import SpellChecker
import sys
import speech_recognition as sr
from textblob import TextBlob
import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore
from pymongo import MongoClient

config ={
   "apiKey": "AIzaSyBv1NhrKqHeYyQMEMTQ_Jook00TcCmk7A8",
    "authDomain": "synduser-aa827.firebaseapp.com",
    "databaseURL": "https://synduser-aa827.firebaseio.com",
    "projectId": "synduser-aa827",
    "storageBucket": "synduser-aa827.appspot.com",
    "messagingSenderId": "663413281362",
    "serviceAccount": "service.json"
}
#FIREBASE CONNECTION FOR MOBILE APPLICATION
firebase=pyrebase.initialize_app(config)
db = firebase.database()
storage = firebase.storage()
cred = credentials.Certificate("./synduser.json")
default_app = firebase_admin.initialize_app(cred)
dab1 = firestore.client()

#WEB APPLICATION
client = MongoClient("mongodb+srv://endgame:endgame22@asgard-pl70h.mongodb.net/test?retryWrites=true&w=majority")            
dbmongo = client.get_database("syndicate")
records = dbmongo.complaint
os.mkdir("raahul")
os.chdir("raahul")
livee = []
files = []

while True:
    file_dir = "Sample"
    file_name = str((db.child("filename").get()).val())+".wav"
    file_name_wav = str((db.child("filename").get()).val())+".wav"
    
    doc_ref = dab1.collection(u'status').document(file_name[:-4])
    doc_ref.update({
    u'statustwo': u'yes'
    })

    
    #DOWNLOADING ALL FILES
    storage.child(file_dir+"/"+file_name).download(file_name_wav) #file_name_full
    if(file_name_wav not in files):
            files.append(file_name_wav)
            #print("IN here")
            os.system("ffmpeg")
            os.system("ffmpeg -i "+file_name+" "+file_name_wav)
            
            livedf= pd.DataFrame(columns=['feature'])
            X, sample_rate = librosa.load(file_name_wav, res_type='kaiser_fast',duration=2.5,sr=22050*2,offset=0.5)
            sample_rate = np.array(sample_rate)
            mfccs = np.mean(librosa.feature.mfcc(y=X, sr=sample_rate, n_mfcc=13),axis=0)
            featurelive = mfccs
            livedf2 = featurelive
            livedf2= pd.DataFrame(data=livedf2)
            livedf2 = livedf2.stack().to_frame().T
            livedf2
            twodim= np.expand_dims(livedf2, axis=2)
            livepreds = loaded_model.predict(twodim,batch_size=32,verbose=1)
            livepreds
            livepreds1=livepreds.argmax(axis=1)
            liveabc = livepreds1.astype(int).flatten()
            livepredictions = (lb.inverse_transform((liveabc)))
            livee.append(livepredictions.item())
            #EMOTION ANALYZED
            file_details = (db.child("tokens/"+file_name[:-4]).get()).val()
            #TEXT CONVERSION
            r = sr.Recognizer()
            with sr.AudioFile(file_name_wav) as source:
                    audio = r.listen(source)
                    try:
                        text=r.recognize_google(audio)
                    except Exception:
                        print("Exception")
            print(text)
            text = text +"."
            
            #SPELL CHECK
            spell = SpellChecker()
            orig_str = text
            new_str = text
            misspelled = list(text.split(' '))
            misspelled = spell.unknown(misspelled)
            for word in misspelled:
                 new_str = new_str.replace(word,spell.correction(word))
            new_str = new_str+"."
            print(new_str)

            #KEYWORD EXTRACTION  DID NOT WORK DUE TO SEMANTIC ERRORS IN TEXT GENERATION 
            from rake_nltk import Rake
            r = Rake()
            r.extract_keywords_from_text(new_str)
            #r.extract_keywords_from_sentences([1])
            words = r.get_ranked_phrases()
            ranks = (r.get_ranked_phrases_with_scores())
            fl = str(max(ranks)) 


            domains=["loan","credit","debit"]
            branch = ["Velachery","Guindy"]
            list1 = []
            list3 =[]
            for dept in domains:
                print(dept)
                i = 0
                #dept = str("loan")
                for word in words:
                    #print(word)
                    if(word == dept):
                        i = i+1
                        print(i)
                list1.append(i)    
            depts = domains[list1.index(max(list1))] 
            sen = text.split()
            for br in branch:
                 print(br)
                 i = 0
                 #dept = str("loan")
                 for word in sen:
                    #print(word)
                     if(word in br):
                         list3.append(word) 
                         break
          
#######################################################################################################################################################################3
 
#REPORT GENERATION            

            data = {}
            data = {
                    'accno':file_details["accno"],
                    'comp_id':file_details["tokenno"],
                    'ifsc':file_details["ifsc"],
                    'mailid':file_details["mailid"],
                    'name':file_details["name"],
                    'phone':file_details["phone"],
                    'domain':depts ,
                    'description':text,
                    'region':"South",
    	            'branch': list3[0],               	               
	                'timestamp':"",#file_details["timestamp"]
                    }
            fj_name = "json"+file_name[:-4]+".json" 
            records.insert_one(data)
            doc_ref.update({
                    u'statusthree': u'no'
                    }) 
    else:
        pass














































































#######################################################################################################
#    import speech_recognition as sr
#    r = sr.Recognizer()
#    filename = "final.wav"
#    with sr.AudioFile(filename) as source:
#    audio = r.listen(source)
#    try:
#        text=r.recognize_google(audio)
#    except Exception:
#        print("Exception")
#        print(text)
##f= open("summary.txt","w+")
##f.write(text)
##f.close()
#
#
##domains=["loan","credit","debit"]
##list1 = []
##list2 = text.split()
##for dept in domains:
##    print(dept)
##    i = 0
##    #dept = str("loan")
##    for word in list2:
##        #print(word)
##        if(word == dept):
##            i = i+1
##            print(i)
##    list1.append(i)    
##depts = domains[list1.index(max(list1))]    
##
#########################################################################################################################################################################3
##from rake_nltk import Rake
##r = Rake()
##r.extract_keywords_from_text(text)
###r.extract_keywords_from_sentences([1])
##r.get_ranked_phrases()
##ranks = (r.get_ranked_phrases_with_scores())
##fl = str(max(ranks))
##
##depts = depts +" "+ fl
##
##db.child("Output/9790844562")
##db.update({"Summary":(str(depts))})