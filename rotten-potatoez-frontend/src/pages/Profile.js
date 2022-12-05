import * as React from "react";
import axios from "axios";
import { Alert, Button, ListItem, ListItemText, TextField } from "@mui/material";
import { Box } from "@mui/system";

const Profile = () => {

    const [username, setUsername] = React.useState("");
    const [firstName, setFirstName] = React.useState("");
    const [lastName, setLastName] = React.useState("");
    const [password, setPassword] = React.useState("");
    const [dateOfBirth, setDateOfBirth] = React.useState("");
    const [email, setEmail] = React.useState("");
    const [alert, setAlert] = React.useState(false);
    const [editMode, setEditMode] = React.useState(false);
    const list = ["First Name", "Last Name", "Username", "Password", "Date of Birth", "Email"];
    const [newUsername, setNewUsername] = React.useState("");
    const [newFirstName, setNewFirstName] = React.useState("");
    const [newLastName, setNewLastName] = React.useState("");
    const [newPassword, setNewPassword] = React.useState("");
    const [newDateOfBirth, setNewDateOfBirth] = React.useState("");
    const [newEmail, setNewEmail] = React.useState("");
    const [disable, setDisable] = React.useState(true);
    const [error, setError] = React.useState("");


    React.useEffect(() => {
        const username = localStorage.getItem("username");
          axios.get(`users/${username}`).then((res) => {
            setUsername(res.data.username);
            setFirstName(res.data.firstName);
            setLastName(res.data.lastName);
            setPassword(res.data.password);
            setDateOfBirth(res.data.dateOfBirth);
            setEmail(res.data.email);
        });
    }, [])

    React.useEffect(() => {
        if (newUsername && newPassword && newFirstName && newLastName && newDateOfBirth && newEmail){
            setDisable(false);
        }
        else{
            setDisable(true);
        }
    }, [newDateOfBirth, newEmail, newFirstName, newLastName, newPassword, newUsername])

    const handleDelete = () => {
        axios.delete(`users/${username}`).then((res) => {
            setAlert(true);
        });
    }

    const handleEditMode = () => {
        setEditMode(true);
    };

    const handleCancel = () => {
        setEditMode(false);
    };

    const handleTextFieldChange = (text, field) => {
        if (field === "First Name"){
            setNewFirstName(text.target.value);
        }
        else if (field === "Last Name"){
            setNewLastName(text.target.value);
        }
        else if (field === "Username"){
            setNewUsername(text.target.value);
        }
        else if (field === "Password"){
            setNewPassword(text.target.value);
        }
        else if (field === "Date of Birth"){
            setNewDateOfBirth(text.target.value);
        }
        else if (field === "Email"){
            setNewEmail(text.target.value);
        }
    }

    const handleEdit = () => {
        axios
      .patch(`users`, {
        username: newUsername,
        password: newPassword,
        firstName: newFirstName,
        lastName: newLastName,
        dateOfBirth: newDateOfBirth,
        email: newEmail,
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
        <>
        <h1>Profile</h1>
        {editMode ?
            <>
            <Box textAlign='center'>
                <ListItem>
                    {list.map((field) => {
                        return (
                            <TextField
                                required
                                fullWidth
                                label={field}
                                onChange={(text) => handleTextFieldChange(text, field)}
                            >
                            </TextField>
                            );
                        })};
                </ListItem>
            </Box>
            </>
            :
            <>
                <ListItem>
                    <ListItemText primary="Username" secondary={username} />
                </ListItem>
                <ListItem>
                    <ListItemText primary="Password" secondary={password} />
                </ListItem>
                <ListItem>
                    <ListItemText primary="First Name" secondary={firstName} />
                </ListItem>
                <ListItem>
                    <ListItemText primary="Last Name" secondary={lastName} />
                </ListItem>
                <ListItem>
                    <ListItemText primary="Email" secondary={email} />
                </ListItem>
                <ListItem>
                    <ListItemText primary="Date of Birth" secondary={dateOfBirth} />
                </ListItem>
        </>}
        <Box textAlign='center'>
            {editMode ?
            <>
                <Button variant="contained" onClick={handleEdit} disabled={disable}>
                    Confirm
                </Button>
                <Button variant="contained" color="error" onClick={handleCancel}>
                    Cancel
                </Button>
            </> :
            <>
                <Button variant="contained" onClick={handleEditMode}>
                    Edit
                </Button>
                <Button variant="contained" color="error" onClick={handleDelete}>
                    Delete
                </Button>
            </> 
            }
            {alert && <Alert severity="error">You have successfully deleted your account.</Alert>}
            {error && <Alert severity="error">{error}</Alert>}
        </Box>
        </>
    );
}

export default Profile;