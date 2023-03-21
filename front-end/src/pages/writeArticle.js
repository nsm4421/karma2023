import axios from "axios";
import { useState } from "react"

export default function WriteArticle(){
    // TODO : 해쉬태그, 이미지 업로드 

    const [title, setTitle] = useState("");
    const [content, setContent] = useState("");
    const [isLoading, setIsLoading] = useState(false);

    const handleTitle = (e) => {setTitle(e.target.value);};
    const handleContent = (e) => {setContent(e.target.value);};

    const handleSubmit = async () => {
        const endPoint = '/api/article/write';
        const data = {title, content, hashtags:[], images:[]};
        setIsLoading(true);
        await axios
            .post(endPoint, data)
            .then((res)=>{
                console.log(res)
            })
            .catch((err)=>{
                alert("에러발생");
                console.log(err);
            })
            .finally(()=>{
                setIsLoading(false);
            })
    }

    return(
        <div>
            <h1>Article 작성 페이지</h1>
            <div>
                <label>제목</label>
                <input value={title} placeholder="제목을 입력하세요" onChange={handleTitle}/>
            </div>
            <div>
                <label>본문</label>
                <textarea value={content} placeholder="본문을 작성하세요" onChange={handleContent}/>
            </div>


            <button disabled={isLoading} onClick={handleSubmit}>제출하기</button>
        </div>
    )
}
