import React from "react";
import {Alert, Button, Container, Stack, TextField} from "@mui/material";
import axios from "axios";
import { useEffect, useState } from "react";
import ProfileCard from "../components/ProfileCard";

const Profile = () => {
    const [user, setUser] = useState();
    const [loading, setLoading] = useState(false);
    const [userId, setUserId] = useState(localStorage.getItem("userId"));
    const [password, setPassword] = useState("");
    const [oldPassword, setOldPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [success, setSuccess] = useState(false);
    const [successMessage, setSuccessMessage] = useState("");
    const [error, setError] = useState(false);
    const [errorMessage, setErrorMessage] = useState("");
    const [visible, setVisible] = useState(false);
    const [visible2, setVisible2] = useState(false);

    const handlePasswordChange = (event) => {
        setPassword(event.target.value);
    };

    const handleOldPasswordChange = (event) => {
        setOldPassword(event.target.value);
    };

    const handleNewPasswordChange = (event) => {
        setConfirmPassword(event.target.value);
    };

  useEffect(() => {
      console.log("user id from local storage is null? " + localStorage.getItem("userId") === "");
      setLoading(true);
      const username = localStorage.getItem("username");
      axios.get(`users/${username}`).then((res) => {
        if(res.data){
            console.log(res.data);
            setUser(res.data);
        }
        setUserId(localStorage.getItem("userId"));
    });
      setLoading(false);
  }, []);

    const handleUpdatePassword = React.useCallback(() => {

        setVisible2(true);
        console.log("Update password button was pressed");

        if(password !== confirmPassword) {
            setError(true);
            setErrorMessage("Passwords don't match.");
        }
        // console.log(oldPassword);
        console.log(user);
        if(oldPassword === user.password){
            console.log(oldPassword);
        }
        else if(visible2) {
            // axios
            //     .post(`users`, {
            //         username: user.username,
            //         password: password,
            //         firstName: user.firstName,
            //         lastName: user.lastName,
            //         dateOfBirth: user.dateOfBirth,
            //         email: user.email,
            //         isAdmin: false
            //     })
            //     .then((res) => {
            //         localStorage.setItem("username", res.data.username);
            //         window.location.href = "/";
            //         setSuccess(true);
            //         setSuccessMessage("Password was updated successfully");
            //     })
            //     .catch((err) => {
            //         setError(err.response.data);
            //     });
            axios.patch("users/change-password/" + userId, {

            }, {params: {
                oldPassword: oldPassword,
                    newPassword: password
            }}).then(res => {
                setSuccess(true);
                setSuccessMessage("Password was updated successfully");
                setVisible2(false);
            }).catch(function (error) {
                setError(true);
                setErrorMessage("Password was not updated. Please check your old password.");
            });
        }
        console.log(errorMessage)
    }, [password, confirmPassword, user, oldPassword, visible2, errorMessage, userId])

    const handleDelete = React.useCallback(() => {

        setVisible(true);
        console.log("Delete button was pressed");
        if(visible) {
            const username = localStorage.getItem("username");
            axios.delete(`users/${username}`).then((res) => {
                console.log(res.data);
              });
        }
        console.log(errorMessage);
    }, [visible, errorMessage])

  return (
      <Container
          sx={{
            paddingTop: "5%",
          }}
      >
          <Stack spacing={2}>
              <h1>Profile</h1>
              {!loading && user != null && (
                  <ProfileCard key={user.id} user={user}/>)
              }
          </Stack>
          <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', marginTop: 20}}>
              { (visible2) && <TextField type="password" label="oldPassword" variant="standard" onChange={handleOldPasswordChange}/>}
              { (visible || visible2) && <TextField type="password" label="password" variant="standard" onChange={handlePasswordChange}/>}
              { (visible2) && <TextField type="password" label="newPassword" variant="standard" onChange={handleNewPasswordChange}/>}

          </div>
          <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', marginTop: 20}}>
              <Button onClick={handleDelete} color="primary">
                  DELETE ACCOUNT
              </Button>
              <Button onClick={handleUpdatePassword} color="primary">
                  Update password
              </Button>
          </div>
          <Stack sx={{ width: '100%' }} spacing={2}>
              {error && <Alert severity="error">{errorMessage}</Alert>}
              {success && <Alert severity="success">{successMessage}</Alert>}
          </Stack>
      </Container>
  );
};
export default Profile;