import * as React from "react";
import axios from "axios";
import { Alert, Button, ListItem, ListItemText } from "@mui/material";
import { Box } from "@mui/system";

const Profile = () => {

    const [username, setUsername] = React.useState("");
    const [firstName, setFirstName] = React.useState("");
    const [lastName, setLastName] = React.useState("");
    const [password, setPassword] = React.useState("");
    const [dateOfBirth, setDateOfBirth] = React.useState("");
    const [email, setEmail] = React.useState("");
    const [alert, setAlert] = React.useState(false);


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

    const handleDelete = () => {
        axios.delete(`users/${username}`).then((res) => {
            setAlert(true);
        });
    }

    return (
        <>
        <h1>Profile</h1>
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
        <Box textAlign='center'>
            <Button variant="contained" color="error" onClick={handleDelete}>
                Delete
            </Button>
            {alert && <Alert severity="error">You have successfully deleted your account.</Alert>}
        </Box>
        </>
    );
}

export default Profile;