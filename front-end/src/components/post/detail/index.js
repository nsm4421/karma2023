import { styled } from '@mui/material/styles';
import Card from '@mui/material/Card';
import CardHeader from '@mui/material/CardHeader';
import CardMedia from '@mui/material/CardMedia';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import Collapse from '@mui/material/Collapse';
import Avatar from '@mui/material/Avatar';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import { red } from '@mui/material/colors';
import FavoriteIcon from '@mui/icons-material/Favorite';
import ShareIcon from '@mui/icons-material/Share';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import MoreVertIcon from '@mui/icons-material/MoreVert';
import ThumbUpIcon from '@mui/icons-material/ThumbUp';
import ThumbDownIcon from '@mui/icons-material/ThumbDown';
import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { Container } from '@mui/system';
import axios from 'axios';

const ExpandMore = styled((props) => {
  const { expand, ...other } = props;
  return <IconButton {...other} />;
})(({ theme, expand }) => ({
  transform: !expand ? 'rotate(0deg)' : 'rotate(180deg)',
  marginLeft: 'auto',
  transition: theme.transitions.create('transform', {
    duration: theme.transitions.duration.shortest,
  }),
}));

const DetailPost = () => {

    const {postId} = useParams();
    const [emotion, setEmotion] = useState("");
    const [likeCount, setLikeCount] = useState({LIKE:0, DISLIKE:0});
    const [expanded, setExpanded] = useState(false);
    const [comments, setComments] = useState([]);
    
    useEffect(()=>{
        getLikeCount();
    }, [])

    useEffect(()=>{
        axios
            .get(`/api/v1/post/${postId}`, {
                headers:{
                    token:localStorage.getItem("token")
                }
            })
            .then((res)=>{
                console.log(res);
            })
            .catch((err)=>{
                console.log(err);
            });
    }, [postId])

    // ------- Request  ------- 
    // TODO : 댓글 페이지네이션
    const getCommentRequest = async ({page}) => {
        await axios
            .get(`/api/v1/comment/${postId}`, {
                headers:{
                    token:localStorage.getItem("token")
                }
            })
            .then((res)=>{
                console.log(res);
                setComments(res.response.data);
            })
            .catch((err)=>{
                console.log(err);
            });
    }

    // 좋아요 & 싫어요 개수
    const getLikeCount = async () => {
        await axios.get(`/api/v1/like/${postId}`, {
            headers:{
                token:localStorage.getItem("token")
            }
        })
        .then((res)=>{
            return res.data.result
        })
        .then((data)=>{
            setLikeCount({LIKE:data.likeCount, DISLIKE:data.dislikeCount});
        })
        .catch((err)=>{
            console.log(err);
        });
    }   

    // 좋아요 & 싫어요 요청
    const sendLikeRequest = async ({likeType}) => {
        await axios.post("/api/v1/like", {postId, likeType}, {
            headers:{
                token:localStorage.getItem("token")
            }
        });
    }   

    // ------- handeler  -------
    // 좋아요 
    const handleLike = (e) => {
        sendLikeRequest(postId, "LIKE")
        if (emotion === "LIKE"){
            setEmotion("");
        } else {
            setEmotion("LIKE")
        }
    }
    // 싫어요
    const handleDisLike = (e) => {
        sendLikeRequest(postId, "DISLIKE")
        if (emotion === "DISLIKE"){
            setEmotion("");
        } else {
            setEmotion("DISLIKE")
        }
    }
    // 댓글창 열기
    const handleExpandClick = () => {
        setExpanded(!expanded);
        getCommentRequest();
    };

    return (
        <Container>
            <Card>
                {/* 작성자 & 작성시간 */}
                <CardHeader
                    avatar={
                    <Avatar sx={{ bgcolor: red[500] }} aria-label="recipe">
                        R
                    </Avatar>
                    }
                    action={
                    <IconButton aria-label="settings">
                        <MoreVertIcon />
                    </IconButton>
                    }
                    title="유저"
                    subheader="작성시간"
                />

                {/* TODO : 이미지 */}
                {/* <CardMedia
                    component="img"
                    height="194"
                    image="/static/images/cards/paella.jpg"
                    alt="Paella dish"
                /> */}

                {/* 본문 */}
                <CardContent>
                    <Typography variant="body2" color="text.secondary">
                        
                    </Typography>
                </CardContent>

                {/* 본문 */}
                <CardActions disableSpacing>
                    {/* 좋아요 아이콘 */}
                    <IconButton onClick={handleLike}>
                        <ThumbUpIcon sx={{color:(emotion==="LIKE")?"red":"gray"}}/> {likeCount.LIKE}
                    </IconButton>
                    {/* 싫어요 아이콘 */}
                    <IconButton onClick={handleDisLike}>
                        <ThumbDownIcon sx={{color:(emotion==="DISLIKE")?"blue":"gray"}}/> {likeCount.DISLIKE}
                    </IconButton>

                    <ExpandMore
                    expand={expanded}
                    onClick={handleExpandClick}
                    aria-expanded={expanded}>
                    <ExpandMoreIcon />
                    </ExpandMore>
                </CardActions>
                
                {/* 댓글 */}
                <Collapse in={expanded} timeout="auto" unmountOnExit>
                    <CardContent>
                        <Typography paragraph>
                            
                        </Typography>
                    </CardContent>
                </Collapse>
            </Card>
        </Container>
  );
}

const sendLikeRequest = async ({postId, likeType}) => {
    const endPoint = "/api/v1/like"
    await axios.post(endPoint, {postId, likeType}, {
        headers:{
            token:localStorage.getItem("token")
        }
    });
}


export default DetailPost;