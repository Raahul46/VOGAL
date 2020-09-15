const express=require("express");
const path=require('path');
const route=express.Router();
const complaint=require('../model/complaint.js');
const MongoClient = require('mongodb').MongoClient;
const uri = "mongodb+srv://regionalmanager:manager@asgard-pl70h.mongodb.net/test?retryWrites=true&w=majority"

    


route.get('/regmanager/grievance',(req,res,next)=>
{
    MongoClient.connect(uri, function(err, client) {
        if(err) {
             console.log('Error occurred while connecting to MongoDB Atlas...\n',err);
        }
        console.log('Connected...');
        const collection = client.db("syndicate").collection("complaint");
        collection.find({branch:'Adyar',domain:'Loan'}).toArray(function(err,result){
            if(err) throw err;1
            console.log(result);
        res.render('Regmangre',{complain:result});
     
        });
     
     
        client.close();
     });

});

module.exports=route;