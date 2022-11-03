import * as React from 'react';
import Box from '@mui/material/Box';
import Drawer from '@mui/material/Drawer';
import Toolbar from '@mui/material/Toolbar';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import ArticleIcon from '@mui/icons-material/Article';

const drawerWidth = 240;

export default function TranscriptMenu({ handleChange }: any) {

    return (
        <Box sx={{ maxHeight: "80vh", overflow: 'auto' }}>
            <List>
                {['Transcript1', 'Transcript2', 'Transcript3', 'Transcript4'].map((text, index) => (
                    <ListItem key={index}>
                        {/* <ListItemButton onClick={handleChange(text)}> */}
                        <ListItemButton>
                            <ListItemIcon>
                                {index % 2 === 0 ? <ArticleIcon /> : <ArticleIcon />}
                            </ListItemIcon>
                            <ListItemText primary={text} />
                        </ListItemButton>
                    </ListItem>
                ))}
            </List>
        </Box>
    );
}
