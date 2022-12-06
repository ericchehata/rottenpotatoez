import * as React from "react";
import axios from "axios";
import { Alert, Button, ListItem, ListItemText, TextField } from "@mui/material";
import { Box } from "@mui/system";

const Profile = () => {

    const [user, setUser] = React.useState({});
    const [alert, setAlert] = React.useState(false);
    const [editMode, setEditMode] = React.useState(false);
    const list = ["First Name", "Last Name", "Password", "Date of Birth", "Email"];
    const [newFirstName, setNewFirstName] = React.useState("");
    const [newLastName, setNewLastName] = React.useState("");
    const [newPassword, setNewPassword] = React.useState("");
    const [newDateOfBirth, setNewDateOfBirth] = React.useState("");
    const [newEmail, setNewEmail] = React.useState("");
    const [disable, setDisable] = React.useState(true);
    const [error, setError] = React.useState("");


    React.useEffect(() => {
        if(!localStorage.getItem('username')) window.location.href = '/signin';
        const username = localStorage.getItem("username");
          axios.get(`users/${username}`).then((res) => {
            setUser(res.data)

            setNewFirstName(res.data.firstName);
            setNewLastName(res.data.lastName);
            setNewPassword(res.data.password);
            setNewDateOfBirth(res.data.dateOfBirth);
            setNewEmail(res.data.email);
        });
    }, [])

    React.useEffect(() => {
        if (newPassword && newFirstName && newLastName && newDateOfBirth && newEmail){
            setDisable(false);
        }
        else{
            setDisable(true);
        }
    }, [newDateOfBirth, newEmail, newFirstName, newLastName, newPassword])

    const handleDelete = () => {
        axios.delete(`users/${user.username}`).then((res) => {
            setAlert(true);
            localStorage.removeItem("username");
            window.location.href = "/signin";
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

    const getValue = (field) => {
        if (field === "First Name"){
            return newFirstName;
        }
        else if (field === "Last Name"){
            return newLastName;
        }
        else if (field === "Password"){
            return newPassword;
        }
        else if (field === "Date of Birth"){
            return newDateOfBirth;
        }
        else if (field === "Email"){
            return newEmail;
        }
    }

    const handleEdit = () => {
        axios
      .patch(`users`, {
        username: user.username,
        password: newPassword,
        firstName: newFirstName,
        lastName: newLastName,
        dateOfBirth: newDateOfBirth,
        email: newEmail,
        isAdmin: user.isAdmin
      })
      .then((res) => {
        setUser(res.data); 
        setEditMode(false);
        setError("");
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
                                key={field}
                                label={field}
                                value={getValue(field)}
                                onChange={(text) => handleTextFieldChange(text, field)}
                            >
                            </TextField>
                            );
                        })}
                </ListItem>
            </Box>
            </>
            :
            <>
                <ListItem>
                    <ListItemText primary="Username" secondary={user.username} />
                </ListItem>
                <ListItem>
                    <ListItemText primary="Password" secondary={user.password} />
                </ListItem>
                <ListItem>
                    <ListItemText primary="First Name" secondary={user.firstName} />
                </ListItem>
                <ListItem>
                    <ListItemText primary="Last Name" secondary={user.lastName} />
                </ListItem>
                <ListItem>
                    <ListItemText primary="Email" secondary={user.email} />
                </ListItem>
                <ListItem>
                    <ListItemText primary="Date of Birth" secondary={user.dateOfBirth} />
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
            {alert && <Alert severity="success">You have successfully deleted your account.</Alert>}
            {error && <Alert severity="error">{error}</Alert>}
        </Box>
        </>
    );
}

export default Profile;