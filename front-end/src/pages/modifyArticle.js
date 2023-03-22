import axios from "axios";
import { useEffect, useState } from "react"
import { useNavigate, useParams } from "react-router-dom";
import { getArticleApi, modifyArticleApi } from "../api/articleApi";
import WriteArticleForm from "../components/writeArticleForm";

export default function ModifyArticle(){   
    
    const params = useParams();
    const navigator = useNavigate();
    const [title, setTitle] = useState("");
    const [content, setContent] = useState("");
    const [hashtags, setHashtags] = useState([""]);
    const [isLoading, setIsLoading] = useState(false); 

    const checkInput = () => {
        if (!title){
            alert("제목을 입력해주세요");
            return;
        }
        if (!content){
            alert("본문을 입력해주세요");
            return;
        }
    }

    const successCallbackForGetArticleApi = (res) => {
        const data = res.data.data;
        setTitle(data.title);
        setContent(data.content);
        setHashtags(data.hashtags);
    }
    const failureCallback = (err)=>{
        console.log(err);
    }

    const successCallbackForModifyArticleApi = () => {
        navigator("/article");
    }

    const handleSubmit = async () => {
        await checkInput();
        setIsLoading(true);
        const data = {articleId:params.id, title, content, hashtags:[...new Set(hashtags)]};
        await modifyArticleApi(data, successCallbackForModifyArticleApi, failureCallback);
    }

    useEffect(()=>{       
        const getArticle = async () => {
            setIsLoading(true);
            await getArticleApi(params.id,successCallbackForGetArticleApi, failureCallback);
            setIsLoading(false);
        }
        getArticle();
    }, []);

    return(
        <div>
            <h1>수정</h1>
            <WriteArticleForm title={title} setTitle={setTitle} content={content} setContent={setContent} hashtags={hashtags} setHashtags={setHashtags}/>
            <button disabled={isLoading} onClick={handleSubmit}>제출</button>
        </div>
    )
}
