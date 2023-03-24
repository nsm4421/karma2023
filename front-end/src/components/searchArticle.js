import { searchArticleApi } from '../api/articleApi';
import { useState } from 'react';
import { Chip, IconButton, InputAdornment, TextField, Typography } from '@mui/material';
import { Search } from '@mui/icons-material';

const InputAdornmentForSearchType = ({searchType, setSearchType}) => {

    const searchTypeList = [
        {label:"제목", value:"TITLE"},
        {label:"본문", value:"CONTENT"},
        {label:"닉네임", value:"NICKNAME"},
    ]

    const handleSearchType = (i) => () => {
        setSearchType(searchTypeList[i].value);
    }

    return (
        <InputAdornment position="start">
            {
                searchTypeList.map((v, i) => {
                    return (
                        <Chip
                            key = {i}
                            label={v.label}
                            color='primary'
                            variant={searchType === v.value ? 'filled' : 'outlined'}
                            onClick={handleSearchType(i)}
                        />
                    )
                })
            }
        </InputAdornment>
    )
}

const InputAdornmentForSearchIcon = ({isLoading, handleSubmit}) => {
    return (
        <InputAdornment  position="start">
            <IconButton
                color='primary'
                sx={{ marginLeft: '10px'}}
                disabled={isLoading}
                onClick={handleSubmit}>
                <Search />
                <Typography>검색</Typography>
            </IconButton>
        </InputAdornment>
    )
}

export default function SearchArticle(props) {

    const handleSearchWord = (e) => {
        props.setSearchWord(e.target.value);
    }

    const handleSubmit = async () => {
        await props.handleSearchArticle();
        props.setSearchWord("");
    };

    return (
        <TextField
            placeholder='검색유형과 검색어를 입력해주세요'
            fullWidth
            variant="standard"
            onChange={handleSearchWord}
            sx={{ minWidth: '500px' }}
            InputProps={{
                startAdornment: <InputAdornmentForSearchType searchType={props.searchType} setSearchType={props.setSearchType} />,
                endAdornment: <InputAdornmentForSearchIcon isLoading={props.isLoading} handleSubmit={handleSubmit} />
            }}
        />
    )
}