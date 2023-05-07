import { useState } from "react";
import { EditorState, convertToRaw } from "draft-js";
import "react-draft-wysiwyg/dist/react-draft-wysiwyg.css";
import { Accordion, Box, Button, Grid, Title } from "@mantine/core";
import ArticleTitleAccordian from "@/components/article/write/article-title-accordian";
import ArticleContentAccrodian from "@/components/article/write/article-content-accordian";
import ArticleHashtagAccordian from "@/components/article/write/article-hashtag-accordian";
import { useRouter } from "next/router";

export default function WriteArticle() {
  const router = useRouter();
  const [title, setTitle] = useState<string>("");
  const [hashtags, setHashtags] = useState<string[]>([""]);
  // 본문
  const [editorState, setEditorState] = useState<EditorState | undefined>(
    EditorState.createEmpty()
  );
  const [isLoading, setIsLoading] = useState<boolean>(false);

  const handleSubmit = async () => {
    const token = localStorage.getItem("token");
    if (!token) {
      alert("You need to login first");
      router.push("/api/auth/login");
      return;
    }
    if (!editorState) {
      alert("Edit State Error");
      return;
    }
    setIsLoading(true);
    await fetch("/api/article/write", {
      method: "POST",
      body: JSON.stringify({
        title,
        content: JSON.stringify(convertToRaw(editorState.getCurrentContent())),
        hashtags : Array.from(new Set(hashtags)),
        token: `Bearer ${localStorage.getItem("token")}`,
      }),
    }).then(res=>{
      if (res.ok){
        router.push("/article")
        alert("Sucess")
        return;
      }
      alert("Fail...")
    }).catch(err=>{
      alert("Fail...")
      console.log(err);
    }).finally(()=>{
      setIsLoading(false);
    })
  };

  return (
    <Box>
      <Grid p="sm" m="sm">
        <Grid.Col span={9}>
          <Title order={3} weight={400} mb="lg">
            Write Article
          </Title>
        </Grid.Col>
        <Grid.Col span={2}>
        </Grid.Col>
        <Grid.Col span={1}>
          <Button onClick={handleSubmit} disabled={isLoading} color="yellow">
            Save
          </Button>
        </Grid.Col>
      </Grid>

      <Accordion
        multiple
        variant="separated"
        p="sm"
        m="sm"
        defaultValue={["title", "content"]}
      >
        {/* 제목 */}
        <ArticleTitleAccordian title={title} setTitle={setTitle} />

        {/* 본문 */}
        <ArticleContentAccrodian
          editorState={editorState}
          setEditorState={setEditorState}
        />

        {/* 해시태그 */}
        <ArticleHashtagAccordian
          hashtags={hashtags}
          setHashtags={setHashtags}
        />
      </Accordion>
    </Box>
  );
}
