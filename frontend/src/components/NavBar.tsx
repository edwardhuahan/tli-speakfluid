import React from 'react';

/***COMPONENTS***/
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import { Link } from 'react-router-dom';

/**
 * The functional component for the navigation bar.
 * @author Sarah Xu
 * @returns The navigation bar as a functional component.
 */
export default function NavBar() {
    return (
        <Box sx={{ flexGrow: 1 }} style={{boxShadow: 'none'}}>
            <AppBar position="static" style={{boxShadow: 'none', backgroundColor: 'white', color: 'black', borderBottom: '2px solid #edede9'}}>
                <Toolbar>
                    <Link to='/' style={{textDecoration: 'none', color: 'black'}}><p className='header'>SpeakFluid</p></Link>
                </Toolbar>
            </AppBar>
        </Box >
    );
}
