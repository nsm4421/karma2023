import useInput from "@/utils/useInput";
import { TextInput, Button, Group, Box } from "@mantine/core";
import { useRouter } from "next/router";
import { Alert } from "@mantine/core";
import { IconAlertCircle } from "@tabler/icons-react";
import useError from "@/utils/useError";
import axios from "axios";

export default function Login() {
  const router = useRouter();
  const [username, , onChangeUsername] = useInput("");
  const [password, , onChangePassword] = useInput("");
  const [isError, errorMessage, handleErrorMessage] = useError();

  function validateInputs(): Boolean {
    // username : 3~30자 이내
    if (username === "") {
      handleErrorMessage("Enter username");
      return false;
    }
    // password : 5~30자 이내
    if (password === "") {
      handleErrorMessage("Enter password");
      return false;
    }
    return true;
  }

  async function handleSubmit() {
    // check inputs
    if (!(await validateInputs())) {
      return;
    }
    const endPoint = "/api/user/login";
    const data = { username, password };
    await axios
      .post(endPoint, data)
      .then((res) => res.data)
      .then((data) => {
        localStorage.setItem("token", data.data);
        router.push("/article");
        console.debug("handleSubmit>>>", data);
      })
      .catch((err) => {
        handleErrorMessage(err.response.data.message ?? err.message);
        console.error("handleSubmit>>>", err);
      });
  }

  return (
    <Box maw={300} mx="auto">
      <h1>Login</h1>
      <Alert
        icon={<IconAlertCircle size="1rem" />}
        title="Sign Up Fail"
        color="red"
        radius="md"
        hidden={!isError}
        onClick={() => {
          handleErrorMessage("");
        }}
      >
        {errorMessage}
      </Alert>

      <TextInput
        withAsterisk
        label={"Username"}
        placeholder={"press username"}
        value={username}
        onChange={onChangeUsername}
      />
      <TextInput
        type="password"
        withAsterisk
        label={"password"}
        placeholder={"press password"}
        value={password}
        onChange={onChangePassword}
      />

      {/* Login Button */}
      <Group position="right" mt="md">
        <Button type="submit" onClick={handleSubmit}>
          Login
        </Button>
      </Group>
    </Box>
  );
}
