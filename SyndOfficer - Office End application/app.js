const express=require('express');

const body_parser=require('body-parser');

const loginroute=require('./route/login.js');

const verify=require('./route/verify.js');

const manager=require('./route/manager.js');

const regmanager=require('./route/regmanager.js');

const man_gre=require('./route/managergre.js');

const regman_gre=require('./route/regmangre.js');

const detailsreg=require('./route/detailsreg.js');

const detailsman=require('./route/detailsman.js');

const statusreg=require('./route/statusreg.js');

const statusman=require('./route/statusman.js');

const path=require('path');

const app=express();


app.set('view engine','pug');
app.set('views','pages');


app.use(body_parser.urlencoded({extended: false}));


app.use(loginroute);

app.use(verify);

app.use(manager);

app.use(regmanager);

app.use(man_gre);

app.use(regman_gre);

app.use(detailsreg);

app.use(detailsman);

app.use(statusreg);

app.use(statusman);

app.use(express.static(path.join(__dirname,'pages')));





app.listen(8000);



