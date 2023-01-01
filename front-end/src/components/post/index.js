import { Box, Container } from "@mui/system";
import axios from "axios";
import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { Button, Typography } from "@mui/material";
import CreateIcon from '@mui/icons-material/Create';
import DynamicFeedIcon from '@mui/icons-material/DynamicFeed';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';

const PostList = ()=>{
    const endPoint = '/api/v1/post'
    const [page, setPage] = useState(0);
    const [posts, setPosts] = useState([]);
    const navigator = useNavigate();
    
    useEffect(()=>{
        axios.get(endPoint, {
            headers:{
                Authorization:localStorage.getItem("token")
            }
        }).then((res)=>{
            return res.data.result.content
        }).then((c)=>{
            setPosts(c);
        })
        .catch((err)=>{
            console.log(err);
        })
    }, [page])

    const handleGoToWritePage = (e) =>{
        navigator("/post/write")    
    }

    return (
        <>
        <Container>

            <Box sx={{marginTop:'5vh', display:'flex', justifyContent:'space-between', alignContent:'center'}}>
                <Typography variant="h5" component="h5">
                    <DynamicFeedIcon/> 포스팅     
                </Typography>
                <Box>
                    <Button variant="contained" color="success" onClick={handleGoToWritePage} sx={{marginRight:'10px'}}>
                        <CreateIcon sx={{marginRight:'10px'}}/>포스팅 쓰기
                    </Button>
                </Box>
            </Box>
            
            {
                posts.map((p, i)=>{
                    return (
                        <Box sx={{marginTop:'5vh'}} key={i}>
                            <PostingCard post={p}/>
                        </Box>
                    )
                })
            }

        </Container>
        </>
    )
}


const PostingCard = ({post}) => {
    const endPoint = `/post/${post.id}`;
    return (
        <Card sx={{ minWidth: 275 }}>
            <CardContent>
                <CardActions sx={{justifyContent:"space-between"}}>
                    {/* 제목 */}
                    <Typography variant="h5" component="span">
                        <Link to={endPoint}> {post.title}</Link>
                    </Typography>
                    {/* 닉네임(작성자) */}
                    <Typography variant="span" component="span" color="text.secondary">
                        {post.nickname}
                    </Typography>
                </CardActions>

                <CardActions sx={{justifyContent:"space-between"}}>
                    {/* 본문 - 100글자까지만 보여주고 ... 붙이기 */}
                    <Box sx={{padding:'1vh'}}>
                        <Typography variant="body2">{post.content.slice(0, 100)}...</Typography>
                    </Box>
                    {/* 닉네임(작성자) */}
                    <Typography variant="span" component="span" color="text.secondary">
                        {post.createdAt}
                    </Typography>
                </CardActions>
                {/* TODO : 페이징 기능 */}
            </CardContent>
        </Card>
    );
  }

export default PostList;