import { useState } from "react";
import { Article } from "@/utils/model";
import {
  IconPhoto,
  IconUser,
  IconNotification,
} from "@tabler/icons-react";
import { Tabs } from "@mantine/core";
import ArticleList from "@/components/article/list/articles-list";

export default function Article() {
  const [currentTab, setCurrentTab] = useState<string | null>("list");

  return (
    <>
      <Tabs
        defaultValue="list"
        value={currentTab}
        onTabChange={setCurrentTab}
        variant="pills"
        color="green"
      >
        <Tabs.List>
          <Tabs.Tab value="list" icon={<IconPhoto />}>
            Articles
          </Tabs.Tab>
          <Tabs.Tab value="my-page" icon={<IconUser />}>
            My Article
          </Tabs.Tab>
          <Tabs.Tab value="notification" icon={<IconNotification />}>
            Notification
          </Tabs.Tab>
        </Tabs.List>

        <Tabs.Panel value="list" pt="xs">
          <ArticleList />
        </Tabs.Panel>

        <Tabs.Panel value="my-page" pt="xs">
      
        </Tabs.Panel>

        <Tabs.Panel value="notification" pt="xs">
          TODO : Notification
        </Tabs.Panel>
      </Tabs>
    </>
  );
}
