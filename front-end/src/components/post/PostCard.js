import { Button, Dialog, DialogContent, DialogTitle, Modal, Typography } from '@mui/material';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import { Box } from '@mui/system';
import { useState } from 'react';
import { Link } from 'react-router-dom';
import DetailPost from './DetailPost';
import FullScreenDialog from './FullScreenDialog';

const PostCard = ({post}) => {
    const [openDialog, setOpenDialog] = useState(false);
    const handleDialog = (e) => {
        e.preventDefault();
        setOpenDialog(!openDialog);
    }

    return (
        <Card sx={{ minWidth: 275 }}>

            {/* 다이얼로그 창 (포스팅 세부정보) */}
            <FullScreenDialog open={openDialog} setOpen={setOpenDialog} post={post}/>
            
            <CardContent onClick={handleDialog}>
                <CardActions sx={{justifyContent:"space-between"}}>
                    {/* 제목 */}
                    <Typography variant="h5" component="strong" color="primary">{post.title}</Typography>
                    {/* 닉네임(작성자) */}
                    <Typography variant="span" component="span" color="text.secondary">
                        {post.nickname}
                    </Typography>
                </CardActions>

                <CardActions sx={{justifyContent:"space-between"}}>
                    {/* 본문 - 100글자까지만 보여주고 ... 붙이기 */}
                    <Box sx={{padding:'1vh'}}>
                        <Typography variant="body2">{post.content.length>=50?post.content.slice(0,50)+" ...":post.content}</Typography>
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

export default PostCard;