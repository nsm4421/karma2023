import { Article } from "@/utils/model";
import ArticleList from "@/components/article/list/articles-list";
import MyNav from "@/components/nav/my-nav";
import { Box, Container } from "@mantine/core";

export default function Article() {

  return (
    <Container>
        <MyNav/>
      <Box mt="lg">
      <ArticleList />
      </Box>
    </Container>
  );
}
