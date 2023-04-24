import useInput from "@/utils/useInput";
import { TextInput, Button, Group, Box } from "@mantine/core";
import { useRouter } from "next/router";
import { Alert } from "@mantine/core";
import { IconAlertCircle } from "@tabler/icons-react";
import useError from "@/utils/useError";
import { css } from "@emotion/react";
import axios from "axios";

export default function SignUp() {
  const router = useRouter();
  const [username, , onChangeUsername] = useInput("");
  const [password, , onChangePassword] = useInput("");
  const [email, , onChangeEmail] = useInput("");
  const [isError, errorMessage, handleErrorMessage] = useError();

  function validateInputs(): Boolean {
    // email
    const emailRegex =
      /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
    if (!email.match(emailRegex)) {
      handleErrorMessage("Email is not valid");
      return false;
    }
    // username : 3~30자 이내
    const usernameRegex = /[a-zA-Z0-9-]{3,30}/;
    if (!username.match(usernameRegex)) {
      handleErrorMessage("Username is invalid");
      return false;
    }
    // password : 5~30자 이내
    const passwordRegex = /[a-zA-Z0-9-]{5,30}/;
    if (!username.match(passwordRegex)) {
      handleErrorMessage("Password is invalid");
      return false;
    }
    return true;
  }

  async function handleSubmit() {
    // check inputs
    if (!(await validateInputs())) {
      return;
    }
    // api call
    const endPoint = "/api/user/signup";
    const data = { username, password, email };
    await axios
      .post(endPoint, data)
      .then((res) => res.data)
      .then((data) => {
        router.push("/auth/login");
        console.debug("handleSubmit >>>", data);
      })
      .catch((err) => {
        handleErrorMessage(err.response.data.message ?? err.message);
        console.error("handleSubmit>>>", err);
      });
  }

  return (
    <Box maw={300} mx="auto">
      <h1>Sign Up</h1>
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
        placeholder={"username for login (3~30 character)"}
        value={username}
        onChange={onChangeUsername}
      />
      <TextInput
        withAsterisk
        label={"Email"}
        placeholder={"press email"}
        value={email}
        onChange={onChangeEmail}
      />
      <TextInput
        type="password"
        withAsterisk
        label={"password"}
        placeholder={"send password (5~30 character)"}
        value={password}
        onChange={onChangePassword}
      />

      {/* Register Button */}
      <Group position="right" mt="md">
        <Button type="submit" onClick={handleSubmit}>
          Sign Up
        </Button>
      </Group>
    </Box>
  );
}
