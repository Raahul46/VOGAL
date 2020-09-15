const express=require("express")
const path=require("path");
const route=express.Router();
const MongoClient = require('mongodb').MongoClient;
const uri = "mongodb+srv://regionalmanager:manager@asgard-pl70h.mongodb.net/test?retryWrites=true&w=majority"


route.post('/reg/status',(req,res,next)=>{  
    
    st=req.body;
    var statu=st.drop;
    str=statu.toString();
    console.log(statu);
    arr=str.split('+');
    id=statu[0];
    var chk=statu[2];
    for(i=3;i<str.length;i++)
        chk+=statu[i];
    num=parseInt(chk);
    
    console.log(id);
    console.log(chk);
    
    MongoClient.connect(uri, function(err, client) {
        if(err) {
             console.log('Error occurred while connecting to MongoDB Atlas...\n',err);
        }
        console.log('Connected...');
        const collection = client.db("syndicate").collection("complaint");
        collection.update({comp_id:num},{$set:{status:id}});
        res.redirect('/regmanager/grievance');
    
       
     
     
        client.close();
     });

    
    });

module.exports=route;