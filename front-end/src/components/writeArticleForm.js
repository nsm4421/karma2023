export default function WriteArticleForm({title, setTitle, content, setContent, hashtags, setHashtags}){
    
    // TODO : 이미지 업로드 
    const MAX_HASHTAG = 5;              // 최대 해시태그 개수
    const handleTitle = (e) => {setTitle(e.target.value); };
    const handleContent = (e) => {setContent(e.target.value);};
    const handleHashtag = (i) => (e) => {
        const _hashtags = [... hashtags];
        _hashtags[i] = e.target.value.replace("#","");      // #는 못쓰게 막기
        setHashtags(_hashtags);
    }
    const addHashtag = () => {
        if (hashtags.length<MAX_HASHTAG){
            setHashtags([...hashtags, ""]);
        }
    }
    const deleteHashtag = (i) => (e) => {
        let _hashtags = [... hashtags];
         _hashtags.splice(i, 1)
        setHashtags(_hashtags);
    }
    return (
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

        <div>
            {
                hashtags.length<MAX_HASHTAG
                ?<button onClick={addHashtag}>해시태그 추가</button>
                :<p>해시태그는 최대 {MAX_HASHTAG}개까지 작성 가능합니다.</p>
            }    

            {
                hashtags.map((v, i)=>{
                    return (
                        <div key={i}>
                            <span>#</span>
                            <input value={v} onChange={handleHashtag(i)}/>
                            <button onClick={deleteHashtag(i)}>삭제</button>
                        </div>
                    )
                })
            }

        </div>
    </div>
    )
}