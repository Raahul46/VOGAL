const express=require("express");
const path=require('path');
const route=express.Router();

route.get('/manager',(req,res,next)=>
{
res.sendFile(path.join(__dirname,'../','pages','Manhome.html'));
});

module.exports=route;