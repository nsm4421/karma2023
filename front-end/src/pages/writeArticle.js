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
    
    const handleSubmit = async () => {
        const successCallback = () => {
            navigator("/article");
        };    
        const failureCallback = console.log;
        await checkInput();
        setIsLoading(true);
        await writeArticleApi(title, content, hashtags, successCallback, failureCallback)
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
