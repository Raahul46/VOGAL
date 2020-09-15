const express=require("express");
const route=express.Router();
const cred=[];
const str="verify"

route.post('/verify',(req,res,next)=>{
    
    var username;
    var password;
    username=req.body.usname;
    password=req.body.password;
    if(username=='hareedharanb'&&password=='iambatman')
        {
            res.redirect('/regmanager');
        }
    
    else if(username=='barathgopi'&&password=='iamsuperman')
        {

         res.redirect('/manager');
        } 
    
   

});
module.exports=route;