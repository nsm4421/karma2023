import axios from "axios";
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";

const WritePost = () => {
    const endPoint = "/api/v1/post";
    const navigator = useNavigate();
    // ------ state ------
    const [title, setTitle] = useState("");
    const [content, setContent] = useState("");
    const [isLoading, setIsLoading] = useState(false);
    const [errorMessage, setErrorMessage] = useState("");

    // ------ handler ------
    const handleTitle = (e) =>{
        setTitle(e.target.value);
    }
    const handleContent = (e) => {
        setContent(e.target.value);
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
            setErrorMessage(err.response.data.resultCode);
            console.log(err);
        }).finally(()=>{
            setIsLoading(false);
        });
    }
    
    return (
        <>
            <h1>Write Post</h1>
            <Link to="/post">포스팅 페이지로</Link>
            <br/>
            <label>제목</label>
            <input onChange={handleTitle}></input>
            <br/>
            <label>본문</label>
            <textarea onChange={handleContent}></textarea>
            <br/>
            <button type="submit" onClick={handleSubmit} disabled={isLoading}>제출</button>
            <p>{errorMessage}</p>
        </>
    )
}

export default WritePost;