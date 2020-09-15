const express=require("express");
const path=require('path');
const route=express.Router();
const MongoClient = require('mongodb').MongoClient;
const uri = "mongodb+srv://regionalmanager:manager@asgard-pl70h.mongodb.net/test?retryWrites=true&w=majority"


route.get('/details/regmanag/:comp_id',(req,res,next)=>
{
    
    ur=req.url;
    console.log(ur);
    var id=ur.split('/');
    comid=parseInt(id[3]);
    console.log(id[3]);
    MongoClient.connect(uri, function(err, client) {
        if(err) {
             console.log('Error occurred while connecting to MongoDB Atlas...\n',err);
        }
        console.log('Connected...');
        const collection = client.db("syndicate").collection("complaint");
        collection.find({comp_id:comid}).toArray(function(err,result){
            if(err) throw err;
            console.log(result);
        res.render('detailsreg',{compid:result});
     
        });
     
     
        client.close();
     });

    
});


module.exports=route;