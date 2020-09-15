const express=require("express")
const path=require("path");
const route=express.Router();


route.get('/login',(req,res,next)=>{  

    res.sendFile(path.join(__dirname,'../','pages','Login.html'))
    
});

module.exports=route;