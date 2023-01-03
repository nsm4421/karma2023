import { styled } from '@mui/material/styles';
import Card from '@mui/material/Card';
import CardHeader from '@mui/material/CardHeader';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import Collapse from '@mui/material/Collapse';
import Avatar from '@mui/material/Avatar';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import { red } from '@mui/material/colors';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import SendIcon from '@mui/icons-material/Send';
import ThumbUpIcon from '@mui/icons-material/ThumbUp';
import ThumbDownIcon from '@mui/icons-material/ThumbDown';
import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { Container } from '@mui/system';
import axios from 'axios';
import { userState } from '../../recoil/user';
import { useRecoilState } from 'recoil';
import { Box, Button, DialogContent, Grid, TextField, Tooltip } from '@mui/material';
import FullScreenDialog from './FullScreenDialog';
import ListItemText from '@mui/material/ListItemText';
import ListItem from '@mui/material/ListItem';
import List from '@mui/material/List';
import Divider from '@mui/material/Divider';

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

const DetailPost = ({postId}) => {

    const [user, setUser] = useRecoilState(userState);
    // 포스팅
    const [title, setTitle] = useState("");
    const [content, setContent] = useState("");
    const [author, setAuthor] = useState("");
    const [createdAt, setCreatedAt] = useState("");
    // 좋아요 & 싫어요
    const [emotion, setEmotion] = useState("");
    const [likeCount, setLikeCount] = useState({LIKE:0, DISLIKE:0});
    // 댓글
    const [comments, setComments] = useState([]);
    const [userComment, setUserComment] = useState("");
    // 기타
    const [expanded, setExpanded] = useState(false);
    const [isLoading, setIsLoading] = useState(false);
    
    useEffect(()=>{
        const endPoint = `/api/v1/post/detail?pid=${postId}`;
        axios
            .get(endPoint, {
                headers:{
                    Authorization:user.token??localStorage.getItem("token")
                }
            })
            .then((res)=>{
                return res.data.result
            })
            .then((res)=>{
                setTitle(res.title);
                setContent(res.content);
                setAuthor(res.nickname);
                setCreatedAt(res.createdAt);
            })
            .catch((err)=>{
                // alert("포스팅을 가져오는데 에러가 발생했습니다..."+endPoint)
                console.log(err);
            });
    }, [])

    // ------- Rest API  ------- 
    // TODO : 댓글 페이지네이션
    const getCommentRequest = async () => {
        const endPoint = `/api/v1/comment/${postId}`
        await axios
            .get(endPoint, {
                headers:{
                    Authorization:user.token??localStorage.getItem("token")
                }
            })
            .then((res)=>{
                setComments([...res.data.result.content]);
            })
            .catch((err)=>{
                console.log(err);
            });
    }

    // 좋아요 & 싫어요 개수
    const getLikeCount = async () => {
        await axios.get(`/api/v1/like/${postId}`, {
            headers:{
                Authorization:user.token??localStorage.getItem("token")
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

    const submitComment = async () => {
        setIsLoading(true);
        const endPoint = `/api/v1/comment`
        await axios.post(endPoint, 
            {id:postId, content:userComment}, 
            {
                headers:{
                    Authorization:user.token??localStorage.getItem("token")
                }
            }).then((res)=>{
                getCommentRequest();
                setUserComment("");
            }).catch((err)=>{
                console.log(err);
            }).finally(()=>{
                setIsLoading(false);
            })
    }

    // 좋아요 & 싫어요 요청
    const sendLikeRequest = async (likeType) => {
        await axios.post("/api/v1/like", {id:postId, likeType}, {
            headers:{
                Authorization:user.token??localStorage.getItem("token")
            }
        });
    }   

    // ------- handeler  -------
  
    // 댓글창 열기
    const handleExpandClick = () => {
        setExpanded(!expanded);
        getCommentRequest();
    };

    // 댓글 최대 500자
    const handleUserComment = (e) => {
        e.preventDefault();
        setUserComment(e.target.value.slice(0, 500));
    }

    // 댓글 입력
    const handleSumbitComment = (e) => {
        e.preventDefault();
        submitComment();
    }


    return (
        <Card>
            {/* 작성자 & 작성시간 */}
            <CardHeader
                avatar={
                    <Avatar sx={{ bgcolor: red[500] }} aria-label="recipe">
                        R
                    </Avatar>
                }
                title={author}
                subheader={createdAt}
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
                <TextField value={content} sx={{width:"100%"}} multiline variant='filled'/>
                <Divider />
            </CardContent>
     

            <CardActions disableSpacing>    
                {/* 좋아요 아이콘 */}
                {/* <IconButton onClick={handleLike}>
                    <ThumbUpIcon sx={{color:(emotion==="LIKE")?"red":"gray"}}/> {likeCount.LIKE}
                </IconButton> */}
                {/* 싫어요 아이콘 */}
                {/* <IconButton onClick={handleDisLike}>
                    <ThumbDownIcon sx={{color:(emotion==="DISLIKE")?"blue":"gray"}}/> {likeCount.DISLIKE}
                </IconButton> */}

                <ExpandMore
                    expand={expanded}
                    onClick={handleExpandClick}
                    aria-expanded={expanded}>
                    <ExpandMoreIcon />
                </ExpandMore>
            </CardActions>
            
            <Collapse in={expanded} timeout="auto" unmountOnExit>
                <Box sx={{ flexGrow: 1, padding:'1vh' }}>
                    {/* 댓글 입력창 */}
                    <Grid container spacing={2}>
                        <Grid item xs={11}>     
                            <Tooltip title="500자 내외로 댓글을 작성해주세요">
                                <TextField label={userComment?`${userComment.length}/500`:"댓글"} sx={{width:'100%'}}
                                variant="standard" multiline onChange={handleUserComment} value={userComment}/>                          
                            </Tooltip>                 
                        </Grid>
                            {/* 댓글 전송 */}
                        <Grid item xs={1}>
                            <Tooltip title="댓글작성">
                                <IconButton onClick={handleSumbitComment} disabled={isLoading} sx={{color:isLoading?"gray":"blue"}}>
                                    <SendIcon/>
                                </IconButton>
                            </Tooltip>
                        </Grid>
                    </Grid>
                </Box>
                <CardContent>                
                    {/* 댓글 */}
                        {
                            comments.map((c, i)=>{
                                return (
                                    <Typography paragraph key={i}>
                                        <Typography variant="span" component="div">
                                            <strong>{c.content}</strong>
                                        </Typography>
                                        <Grid container>
                                            <Grid item xs={4}>
                                                <Typography sx={{ mb: 1.5 }} color="text.secondary">
                                                    by {c.nickname}
                                                </Typography>
                                            </Grid>
                                            <Grid item xs={4}>
                                            </Grid>
                                            <Grid item xs={4}>
                                                <Typography sx={{ mb: 1.5 }} color="text.secondary">
                                                    {c.createdAt}
                                                </Typography>
                                            </Grid>
                                        </Grid>                                          
                                    </Typography>
                                )
                            })
                        }
                </CardContent>
            </Collapse>
        </Card>
  );
}


export default DetailPost;