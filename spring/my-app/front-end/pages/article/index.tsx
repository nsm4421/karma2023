import { useState } from "react";
import { Article } from "@/utils/model";
import {
  IconPhoto,
  IconPencil,
  IconUser,
} from "@tabler/icons-react";
import ArticleList from "@/components/article/articles-list";
import { Tabs } from "@mantine/core";

export default function Article() {
  const [currentTab, setCurrentTab] = useState<string | null>("list");

  return (
    <>
      <Tabs defaultValue="list" value={currentTab} onTabChange={setCurrentTab} variant="pills" color="green">
        <Tabs.List>
          <Tabs.Tab value="list" icon={<IconPhoto/>}>
            Articles
          </Tabs.Tab>
          <Tabs.Tab value="write" icon={<IconPencil />}>
            Write
          </Tabs.Tab>
          <Tabs.Tab value="settings" icon={<IconUser />}>
            My Article
          </Tabs.Tab>
        </Tabs.List>

        <Tabs.Panel value="list" pt="xs">
          <ArticleList />
        </Tabs.Panel>

        <Tabs.Panel value="write" pt="xs">
          Messages tab content
        </Tabs.Panel>

        <Tabs.Panel value="settings" pt="xs">
          Settings tab content
        </Tabs.Panel>
      </Tabs>
    </>
  );
}
