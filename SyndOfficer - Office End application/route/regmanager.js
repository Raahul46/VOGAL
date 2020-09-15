const express=require("express");
const path=require('path');
const route=express.Router();


route.get('/regmanager',(req,res,next)=>
{
res.sendFile(path.join(__dirname,'../','pages','Regmanhome.html'));
});

module.exports=route;