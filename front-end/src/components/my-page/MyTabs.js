import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import Box from '@mui/material/Box';
import { Typography } from '@mui/material';
import DynamicFeedIcon from '@mui/icons-material/DynamicFeed';
import NotificationsNoneIcon from '@mui/icons-material/NotificationsNone';
import ManageAccountsIcon from '@mui/icons-material/ManageAccounts';

const MyTabs = ({tabSelect, setTabSelect}) => {
    
    const MyPostingLabel = () => {
        return (
            <Box sx={{alignContent:"center", display:"flex"}}>
                <DynamicFeedIcon/>
                <Typography variant="span" sx={{marginLeft:"10px"}}>{"내 포스팅보기"}</Typography> 
            </Box>
        )
    }

    const MyAccountLabel = () => {
        return (
            <Box sx={{alignContent:"center", display:"flex"}}>
                <ManageAccountsIcon/>
                <Typography variant="span" sx={{marginLeft:"10px"}}>{"내 계정설정"}</Typography> 
            </Box>
        )
    }

    const MyNotificationLabel = () => {
        return (
            <Box sx={{alignContent:"center", display:"flex"}}>
                <NotificationsNoneIcon/>
                <Typography variant="span" sx={{marginLeft:"10px"}}>{"알림보기"}</Typography> 
            </Box>
        )
    }

    const labels = [<MyPostingLabel/>, <MyAccountLabel/>, <MyNotificationLabel/>]

    const handleSelect = (e, v) => {
        setTabSelect(v);
    }
    return(
        <Box sx={{ width: '100%', marginTop:'3vh'}}>
            <Tabs value={tabSelect} onChange={handleSelect}>
                {
                    labels.map((l, i)=>{
                        return (
                            <Tab key={i} value={i} label={l}/>
                        )
                    })
                }
            </Tabs>
        </Box>
    )
}

export default MyTabs;