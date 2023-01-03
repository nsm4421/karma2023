import axios from "axios";
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import CreateIcon from '@mui/icons-material/Create';
import { Button, TextField, Typography } from "@mui/material";
import { Box, Container } from "@mui/system";
import DynamicFeedIcon from '@mui/icons-material/DynamicFeed';
import UploadIcon from '@mui/icons-material/Upload';

const WritePost = () => {
    const endPoint = "/api/v1/post";
    const navigator = useNavigate();
    // ------ state ------
    const [title, setTitle] = useState("");
    const [content, setContent] = useState("");
    const [isLoading, setIsLoading] = useState(false);

    // ------ handler ------
    const handleTitle = (e) =>{
        setTitle(e.target.value.slice(0, 100));
    }
    const handleContent = (e) => {
        setContent(e.target.value.slice(0, 2000));
    }
    const handleSubmit = async (e) =>{
        e.preventDefault();
        setIsLoading(true);
        await axios.post(
            endPoint,
            {title, content},
            {
                headers:{
                    Authorization : localStorage.getItem("token")
                }
            }
        ).then((res)=>{
            navigator("/post");
        }).catch((err)=>{
            alert("포스팅 업로드에 실패하였습니다 \n" + (err.response.data.resultCode??"알수 없는 서버 오류"));
            console.log(err);
        }).finally(()=>{
            setIsLoading(false);
        });
    }
    
    return (
        <>
        <Container>

            <Box sx={{marginTop:'5vh', display:'flex', justifyContent:'space-between', alignContent:'center'}}>
                <Typography variant="h5" component="h5">
                    <CreateIcon/> 포스팅 작성하기
                </Typography>
                <Box>
                    <Link to="/post">
                        <Button variant="contained" color="success" sx={{marginRight:'10px'}}>
                            <DynamicFeedIcon sx={{marginRight:'10px'}}/>포스팅 페이지로
                        </Button>
                    </Link>
                    <Button variant="contained" color="error" type="submit" onClick={handleSubmit} disabled={isLoading}>
                        <UploadIcon sx={{marginRight:'10px'}}/>제출
                    </Button>
                </Box>
            </Box>

            <Box sx={{marginTop:'5vh'}}>
            <Box sx={{alignItems:'center'}}>
                    <Typography variant="h6" component="h6" sx={{display:'inline', marginRight:'20px'}}>제목</Typography>
                    <Typography variant="span" component="span" sx={{color:'gray'}}>
                        ({title.length} / 100)
                    </Typography>
                </Box>
                <TextField
                    sx={{width:'100%'}}
                    onChange={handleTitle}
                    variant="outlined"
                    color="warning"
                    maxRows={1}
                    value={title}
                    focused
                />  
            </Box>

            <Box sx={{marginTop:'5vh'}}>
                <Box sx={{alignItems:'center'}}>
                    <Typography variant="h6" component="h6" sx={{display:'inline', marginRight:'20px'}}>본문</Typography>
                    <Typography variant="span" component="span" sx={{color:'gray'}}>
                        ({content.length} / 2000)
                    </Typography>
                </Box>
                <TextField
                    sx={{width:'100%', height:'50vh'}}
                    onChange={handleContent}
                    variant="outlined"
                    color="warning"
                    value={content}
                    multiline
                    focused
                />
            </Box>
        </Container>
        </>
    )
}

export default WritePost;