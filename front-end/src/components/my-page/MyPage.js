import { useState } from "react";
import { Container } from '@mui/system';
import MyTabs from './MyTabs';
import MyPostList from "./MyPostList";
import { useRecoilState } from "recoil";
import { userState } from "../../recoil/user";

const MyPage = () => {

    const [user, setUser] = useRecoilState(userState);
    const [tabSelect, setTabSelect] = useState(0);
   
    return (
        <>
            <Container>
                <MyTabs tabSelect={tabSelect} setTabSelect={setTabSelect}/>
                {
                    tabSelect===0
                    ?<MyPostList nickname={user.nickname}/>
                    :null
                }
            </Container>
        </>
    );
}

export default MyPage;