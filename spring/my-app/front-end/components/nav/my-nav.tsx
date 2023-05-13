import { Avatar, Badge, Grid, Group, Title, Tooltip } from "@mantine/core";
import { IconNotification } from "@tabler/icons-react";
import { useRouter } from "next/router";
import { useEffect, useState } from "react";

const navItmes = [
  {
    label: "Home",
    link: "/",
  },
  {
    label: "Article",
    link: "/article",
  },
  {
    label: "Wrtie",
    link: "/article/write",
  },
  {
    label: "Alarm",
    link: "/notification",
  },
];

export default function MyNav() {
  const router = useRouter();
  const [current, setCurrent] = useState<number>(0);
  const [username, setUsername] = useState<string | null>(null);
  const [isLogined, setIsLogined] = useState<boolean>(false);

  const handleClick = (link: string) => () => {
    router.push(link);
  };

  const getUsername = async () => {
    setIsLogined(false);
    const token = localStorage.getItem("token");
    try {
      if (!token) return;
      const data = await fetch("/api/user/get-username", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
        .then((res) => res.json())
        .then((json) => json.data)
        .catch((err) => console.error(err));
      setUsername(data);
      setIsLogined(true);
    } catch (err) {
      setIsLogined(false);
      console.error(err);
    }
  };

  useEffect(() => {
    getUsername();
  }, []);

  useEffect(() => {
    navItmes.forEach((item, idx) => {
      if (item.link === router.pathname) {
        setCurrent(idx);
      }
    });
  }, []);

  return (
    <Grid justify="space-between" align="center">
      <Grid.Col span={2}>
        <Title order={3}>Gallery</Title>
      </Grid.Col>
      <Grid.Col span={5}>
        <Group>
          {navItmes.map((item, idx) => (
            <Badge
              key={idx}
              size="lg"
              color={current === idx ? "teal" : "gray"}
              variant={current === idx ? "filled" : "outline"}
              onClick={handleClick(item.link)}
            >
              {item.label}
            </Badge>
          ))}
        </Group>
      </Grid.Col>
      <Grid.Col span={2} offset={3}>
        {isLogined && (
          <Group>
            <Tooltip label={username}>
              <Avatar />
            </Tooltip>
            <IconNotification />
          </Group>
        )}
      </Grid.Col>
    </Grid>
  );
}
