import axios from "axios";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

const Post = ()=>{
    const endPoint = '/api/v1/post'
    const [page, setPage] = useState(0);
    const [posts, setPosts] = useState([]);
    

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


    return (
        <>
        <h1>포스팅 페이지</h1>
        <Link to="/post/write">포스팅 쓰기</Link>
            {
                posts.map((p, i)=>{
                    return (
                        <div key={i}>
                            <p>제목</p>
                            <p>{p.title}</p>
                            <p>본문</p>
                            <p>{p.content}</p>
                        </div>
                    )
                })
            }
        </>
    )
}

export default Post;