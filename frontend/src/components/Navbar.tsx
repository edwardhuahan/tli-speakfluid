import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';
import UploadButton from './UploadButton';
import { Image } from '@mui/icons-material';

export default function NavBar({ handleToggle }: any) {



    return (
        <Box sx={{ flexGrow: 1 }}>
            <AppBar position="static">
                <Toolbar>
                    <IconButton
                        size="large"
                        edge="start"
                        color="inherit"
                        aria-label="menu"
                        sx={{ mr: 2 }}
                        onClick={handleToggle}
                    >
                        <MenuIcon />
                    </IconButton>
                    <img src='../logo192.png' width="140px" height="40px"></img>
                    {/* <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                        Speakfluid
                    </Typography> */}
                    <Box sx={{ ml: 'auto' }}>
                        <UploadButton />
                    </Box>
                </Toolbar>
            </AppBar>
        </Box >
    );
}
