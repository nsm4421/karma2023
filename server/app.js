const express = require('express');
const fileUpload = require('express-fileupload');
const app = express();

app.use(fileUpload());
app.use('/images/profile', express.static('images/profile'));

app.post('/upload', (req, res)=>{
    let uploadFile = req.files?.picture;
    let uploadPath = `${__dirname}/images/profile/${uploadFile.name}`;
    uploadFile.mv(uploadPath, (err)=>{
    if (err) return res.status(500).send(err);
        console.log('err')
        res.send(`/images/profile/${uploadFile.name}`);
    });
})

app.listen(3000, ()=>{
    return console.log('Server for image upload is running on port 3000...')
});