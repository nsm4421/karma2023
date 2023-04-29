import ArticleCard from "@/components/article-card";
import Loading from "@/components/loading";
import axios from "axios";
import { Box, Grid, Pagination } from "@mantine/core";
import { useEffect, useState } from "react";
import { SearchType, SortField, Article, Direction } from "@/utils/model";
import { IconArticle, IconPencil, IconUser } from '@tabler/icons-react';

const tabs = [
  {
    label:"Articles",
    value:"list",
    icon:<IconArticle />
  },
  {
    label:"Write",
    value:"write",
    icon:<IconPencil />
  },
  {
    label:"My Page",
    value:"my",
    icon:<IconUser />
  },
]

export default function Article() {
  const [currentTab, setCurrentTab] = useState<Number>(0);
  const [pageNumber, setPageNumber] = useState<number>(1);
  const [totalPages, setTotalpages] = useState<number>(1);
  const [size, setSize] = useState<number>(20);
  const [searchType, setSearchType] = useState<SearchType>(null);
  const [searchText, setSearchText] = useState<String | null>("");
  const [sortField, setSortField] = useState<SortField>(null);
  const [direction, setDirection] = useState<Direction>(null);
  const [articles, setArticles] = useState<Article[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(false);

  const getEndPoint = () =>
    `http://localhost:8080/api/article?page=${pageNumber - 1}&size=${size}` +
    (searchType && searchText
      ? `&search-type=${searchType}&seach-text=${searchText}`
      : "") +
    (sortField && direction
      ? `&sort-field=${sortField}&direction=${direction}`
      : "");

  const getArticles = async () => {
    setIsLoading(true);
    const endPoint = getEndPoint();
    console.debug(endPoint);
    await axios
      .get(endPoint)
      .then((res) => res.data.data)
      .then((data) => {
        setArticles([...data.content]);
        setTotalpages(data.totalPages);
        console.log("getArticles>>>", data);
      })
      .catch((err) => {
        console.error(err);
      });
    setIsLoading(false);
  };

  useEffect(() => {
    getArticles();
  }, []);

  if (isLoading) {
    return <Loading />;
  }

  return (
    <>
      <h1>Articles</h1>

      {articles.map((article, idx) => (
        <Box key={idx} m="sm" p="sm">
          <ArticleCard article={article} />
        </Box>
      ))}   

      {/* 페이지네이션 바 */}
      <Grid justify="center" mt={"md"} mb={"md"}>
        <Pagination
          total={totalPages}
          color="red"
          radius="lg"
          siblings={2}
          withEdges
          value={pageNumber}
          onChange={setPageNumber}
        />
      </Grid>
    </>
  );
}
