import * as React from "react";
import axios from "axios";
import {
  Alert,
  Avatar,
  Box,
  Button,
  Container,
  CssBaseline,
  FormControl,
  Grid,
  Link,
  TextField,
  Typography,
} from "@mui/material";
import { DatePicker, LocalizationProvider } from "@mui/x-date-pickers";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";

const Signup = () => {
  const [firstName, setFirstName] = React.useState();
  const [lastName, setLastName] = React.useState();
  const [email, setEmail] = React.useState();
  const [dateOfBirth, setDateOfBirth] = React.useState(null);
  const [username, setUsername] = React.useState();
  const [password, setPassword] = React.useState();
  const [confirmPassword, setConfirmPassword] = React.useState();
  const [error, setError] = React.useState();


  const handleFirstNameChange = (event) => {
    setFirstName(event.target.value);
  };

  const handleLastNameChange = (event) => {
    setLastName(event.target.value);
  };

  const handleEmailChange = (event) => {
    setEmail(event.target.value);
  };

  const handleDateOfBirthChange = (newValue) => {
    setDateOfBirth(newValue);
  };

  const handleUsernameChange = (event) => {
    setUsername(event.target.value);
  };

  const handlePasswordChange = (event) => {
    setPassword(event.target.value);
  };

  const handleConfirmPasswordChange = (event) => {
    setConfirmPassword(event.target.value);
  };

  const signup = () => {
    if(password !== confirmPassword) {
      setError("Passwords do not match");
      return;
    }
    axios
      .post(`users`, {
        username: username,
        password: password,
        firstName: firstName,
        lastName: lastName,
        dateOfBirth: dateOfBirth,
        email: email,
        isAdmin: false
      })
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
          marginBottom: 5,
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
        }}
      >
        <Avatar sx={{ m: 1, bgcolor: "primary" }}>
          <LockOutlinedIcon />
        </Avatar>
        <Typography component="h1" variant="h5">
          Sign up
        </Typography>
        <Box sx={{ mt: 3 }}>
          <Grid container spacing={2}>
            <Grid item xs={12} sm={6}>
              <TextField
                autoComplete="given-name"
                name="firstName"
                required
                fullWidth
                id="firstName"
                label="First Name"
                autoFocus
                onChange={handleFirstNameChange}
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                required
                fullWidth
                id="lastName"
                label="Last Name"
                name="lastName"
                autoComplete="family-name"
                onChange={handleLastNameChange}
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                required
                fullWidth
                id="email"
                label="Email Address"
                name="email"
                autoComplete="email"
                onChange={handleEmailChange}
              />
            </Grid>
            <Grid item xs={12} sm={6}>
            <FormControl fullWidth required>
              <LocalizationProvider dateAdapter={AdapterDayjs}>
                <DatePicker
                  id="dateOfBirth"
                  label="Date of Birth"
                  name="dateOfBirth"
                  value={dateOfBirth}
                  onChange={handleDateOfBirthChange}
                  renderInput={(params) => <TextField {...params} />}
                />
              </LocalizationProvider>
            </FormControl>
            </Grid>
            <Grid item xs={12}>
              <TextField
                required
                fullWidth
                name="username"
                label="Username"
                type="username"
                id="username"
                autoComplete="new-username"
                onChange={handleUsernameChange}
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                required
                fullWidth
                name="password"
                label="Password"
                type="password"
                id="password"
                autoComplete="new-password"
                onChange={handlePasswordChange}
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                required
                fullWidth
                name="confirmPassword"
                label="Confirm Password"
                type="password"
                id="confirmPassword"
                autoComplete="new-password"
                onChange={handleConfirmPasswordChange}
              />
            </Grid>
          </Grid>
          <Button
            type="submit"
            fullWidth
            variant="contained"
            sx={{ mt: 3, mb: 2 }}
            disabled={!firstName || !lastName || !email || !dateOfBirth || !username || !password}
            onClick={signup}
          >
            Sign Up
          </Button>
          <Grid container>
            <Grid item>
              <Link href="/signin" variant="body2">
                Already have an account? Sign in
              </Link>
            </Grid>
          </Grid>
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

export default Signup;
