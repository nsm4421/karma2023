import { Article } from "@/utils/model";
import axios from "axios";
import { useEffect, useState } from "react";
import MyEditor from "../write/edit-article";
import { EditorState, convertFromRaw } from "draft-js";
import Loading from "@/components/loading";
import {
  Avatar,
  Badge,
  Box,
  Divider,
  Group,
  Paper,
  Text,
  Title,
} from "@mantine/core";
import "react-draft-wysiwyg/dist/react-draft-wysiwyg.css";
import EmotionButton from "./emotion-button";

export default function ArticleContent({ id }: { id: string }) {
  const [article, setArticle] = useState<Article | null>(null);
  const [editorState, setEditorState] = useState<EditorState | undefined>(
    undefined
  );
  const [isLoading, setIsLoading] = useState<boolean>(false);

  const getArticle = async (id: string) => {
    setIsLoading(true);
    await axios
      .get(`http://localhost:8080/api/article/${id}`)
      .then((res) => res.data)
      .then((data) => {
        setEditorState(
          EditorState.createWithContent(
            convertFromRaw(JSON.parse(data.data.content))
          )
        );
        setArticle(data.data);
      })
      .catch((err) => {
        alert("Error occurs...");
        console.error(err);
      });
    setIsLoading(false);
    return;
  };

  useEffect(() => {
    getArticle(id);
  }, []);

  //   에러
  if (!(article && editorState)) {
    return (
      <>
        <Text>Error</Text>
      </>
    );
  }

  // 로딩중
  if (isLoading) {
    return <Loading />;
  }

  return (
    <>
      <Paper shadow="md" m="sm" p="md" withBorder>
        <Group position="apart">
          {/* 제목 */}
          <Group>
            <Badge>Title</Badge>
            <Title order={6}>{article.title}</Title>
          </Group>
          {/* 작성자 */}
          <Badge
            pl={0}
            size="md"
            color="teal"
            radius="xl"
            leftSection={<Avatar size="sm" />}
          >
            {article.createdBy}
          </Badge>
        </Group>
      </Paper>

      <Paper shadow="md" m="sm" p="md" withBorder>
        <Badge pt="sm" pb="sm" mb="sm">
          Content
        </Badge>
        <Divider />
        {/* 본문 */}
        <Box p="sm" mb="md">
          <MyEditor readOnly={true} editorState={editorState} />
        </Box>
        <Divider />
        <Group position="apart">
          {/* 해시태그 */}
          <Box maw={400} pt="sm">
            {Array.from(article.hashtags).map((hashtag, idx) => (
              <Badge key={idx} color="green">
                #{hashtag}
              </Badge>
            ))}
          </Box>
          {/* 좋아요/싫어요 버튼 */}
          <EmotionButton id={id} />
        </Group>
      </Paper>
    </>
  );
}
