import {
  Alert,
  Avatar,
  Box,
  Button,
  Container,
  CssBaseline,
  Link,
  TextField,
  Typography,
} from "@mui/material";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import axios from "axios";
import * as React from "react";

const Signin = () => {
  const [username, setUsername] = React.useState();
  const [password, setPassword] = React.useState();
  const [error, setError] = React.useState();

  const handleUsernameChange = (event) => {
    setUsername(event.target.value);
  };

  const handlePasswordChange = (event) => {
    setPassword(event.target.value);
  };

  const login = () => {
    axios
      .post(`users/login?username=${username}&password=${password}`)
      .then((res) => {
        localStorage.setItem("username", res.data.username);
        window.location.href = "/";
      })
      .catch((err) => {
        setError(err.response.data);
      });
  };

  return (
    <Container component="main" maxWidth="xs">
      <CssBaseline />
      <Box
        sx={{
          marginTop: 8,
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
        }}
      >
        <Avatar sx={{ m: 1, bgcolor: "primary.main" }}>
          <LockOutlinedIcon />
        </Avatar>
        <Typography component="h1" variant="h5">
          Sign in
        </Typography>
        <Box sx={{ mt: 1 }}>
          <TextField
            margin="normal"
            required
            fullWidth
            id="email"
            label="Email Address"
            name="email"
            autoFocus
            onChange={handleUsernameChange}
          />
          <TextField
            margin="normal"
            required
            fullWidth
            name="password"
            label="Password"
            type="password"
            id="password"
            onChange={handlePasswordChange}
          />
          <Button
            type="submit"
            fullWidth
            variant="contained"
            sx={{ mt: 3, mb: 2 }}
            disabled={!username || !password}
            onClick={login}
          >
            Sign In
          </Button>
          <Link href="/signup" variant="body2">
            {"Don't have an account? Sign Up"}
          </Link>
          {error && (
            <Alert severity="error" sx={{mt: 2}}>
              {error}
            </Alert>
          )}
        </Box>
      </Box>
    </Container>
  );
};
export default Signin;
