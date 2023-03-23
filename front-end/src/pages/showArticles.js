import { useEffect, useState } from "react";
import { getArticlesApi, searchArticleApi } from "../api/articleApi";
import Article from "../components/article";

export default function ShowArticle(){

    const [searchType, setSearchType] = useState("TITLE");
    const [searchWord, setSearchWord] = useState("");
    const [isLoading, setIsLoading] = useState(false);
    const [articles, setArticles] = useState([]);
    const [pagebale, setPageable] = useState({});
    const [totalPages, setTotalPages] = useState(1);
    const [totalElements, setTotalElements] = useState(1);

    const handleSearchType = (e) => {
        setSearchType(e.target.value);
    }

    const handleSearchWord = (e) => {
        setSearchWord(e.target.value);
    }    

    const successCallback = (res) => {
        const data = res.data.data;
        console.log(data);
        setArticles([...data.content]);
        setPageable(data.pagebale);
        setTotalPages(data.totalPages);
        setTotalElements(data.totalElements);
    }

    const failureCallback = console.log;
            
    const handleSubmit = () => {
        setIsLoading(true);
        searchArticleApi(searchType, searchWord, successCallback, failureCallback);
        setIsLoading(false);
    };

    useEffect(()=>{
        getArticlesApi(successCallback, failureCallback);
    }, [])

    if (isLoading){
        return (
            <div>
                <h1>로딩중...</h1>
            </div>
        )
    }

    return (
        <div>
            <h1>Show Article</h1>
            
            <div>
                <h3>검색바</h3>

                <label>검색유형</label>
                <select onChange={handleSearchType}>
                    <option value="TITLE">제목</option>
                    <option value="CONTENT">본문</option>
                    <option value="NICKNAME">닉네임</option>
                </select>
                <br/>

                <input value={searchWord} onChange={handleSearchWord} placeholder="검색할 단어를 입력하세요"/>
                <br/>

                <button disabled={isLoading} onClick={handleSubmit}>검색하기</button>
            </div>                      
            
            {
                articles.map((a, i)=>{
                    return (
                        <div key={i}>
                            <Article article={a} isLoading={isLoading} setIsLoading={setIsLoading}/>
                            <hr/>
                        </div>
                    )
                })
            }

            <p>전체 게시글수 : {totalElements}</p>
            <p>총 페이지수 : {totalPages}</p>
        </div>
    )
}