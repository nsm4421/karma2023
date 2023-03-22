import axios from "axios";
import { useState } from "react"
import { useNavigate } from "react-router-dom";
import { writeArticleApi } from "../api/articleApi";
import WriteArticleForm from "../components/writeArticleForm";

export default function WriteArticle(){   

    // TODO : 이미지 업로드 
    const navigator = useNavigate();
    const [title, setTitle] = useState("");
    const [content, setContent] = useState("");
    const [hashtags, setHashtags] = useState([""]);
    const [isLoading, setIsLoading] = useState(false);

    const checkInput = async () => {
        if (!title){
            alert("제목을 입력해주세요");
            return;
        }
        if (!content){
            alert("본문을 입력해주세요");
            return;
        }
    }

    const successCallbackForWriteArticleApi = () => {
        navigator("/article");
    }

    const failureCallback = console.log;
    
    const handleSubmit = async () => {
        await checkInput();
        const data = {title, content, hashtags:[...new Set(hashtags)]};     // 해시태그는 중복 제거해서 보냄
        setIsLoading(true);
        await writeArticleApi(data, successCallbackForWriteArticleApi, failureCallback)
        setIsLoading(false);
    }

    return(
        <div>
            <h1>작성</h1>
            <WriteArticleForm title={title} setTitle={setTitle} content={content} setContent={setContent} hashtags={hashtags} setHashtags={setHashtags}/>
            <button disabled={isLoading} onClick={handleSubmit}>제출</button>
        </div>
    )
}
