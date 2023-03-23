import { useState } from "react"
import { searchArticleApi } from '../api/articleApi';

export default function SearchArticle({successCallback, setArticles, setPageable, setTotalPages, setTotalElements, isLoading}){

    const [searchType, setSearchType] = useState("TITLE");
    const [searchWord, setSearchWord] = useState("");

    const handleSearchType = (e) => {
        setSearchType(e.target.value);
    }

    const handleSearchWord = (e) => {
        setSearchWord(e.target.value);
    }    
        
    const handleSubmit = async () => {
        const successCallback = (res) => {
            const data = res.data.data; 
            console.log(res);
            setArticles([...data.content]);
            setPageable(data.pagebale);
            setTotalPages(data.totalPages);
            setTotalElements(data.totalElements);
        }
        const failureCallback = console.log;
        await searchArticleApi(searchType, searchWord, successCallback, failureCallback)
    };

    return (
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
    )
}