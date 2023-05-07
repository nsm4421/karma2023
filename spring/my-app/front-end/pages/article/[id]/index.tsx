import ArticleContent from "@/components/article/detail/article-content";
import ArticleComment from "@/components/article/detail/article-comment";
import { Accordion } from "@mantine/core";
import { useRouter } from "next/router";
export default function ArticleDetail() {
  const router = useRouter();
  const { id } = router.query;

  if (!(typeof id === "string")) {
    return (
      <>
        <h1>Error</h1>
        <p>Article id is not valid : {id}</p>
      </>
    );
  }
  return (
    <>
        <ArticleContent id={id} />

      <ArticleComment id={id} />
    </>
  );
}
