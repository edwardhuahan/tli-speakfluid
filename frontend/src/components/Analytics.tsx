import * as React from 'react';
import Box from '@mui/material/Box';
import Chip from '@mui/material/Chip';
import Button from '@mui/material/Button';
import Grid from '@mui/material/Grid';
import Stack from '@mui/material/Stack';
import Divider from '@mui/material/Divider';
import Typography from '@mui/material/Typography';
import AnalyticGraphs from "./AnalyticGraphs";
import AnalyticsCard from "./AnalyticsCard";

export default function Analytics() {
    return (
        <Box sx={{ width: '100%', bgcolor: 'background.paper' }}>
            <Box sx={{ my: 3, mx: 2 }}>
                <Typography gutterBottom variant="h4" component="div">
                    What can I help you with today?
                </Typography>
                <Box>
                    <Stack direction="row" spacing={1}>
                        <Chip label="Text" />
                        <Chip color="primary" label="Cards" />
                        <Chip label="Choice" />
                        <Chip label="Button" />
                        <Chip label="Capture" />
                        <Chip label="Carousel" />
                    </Stack>
                </Box>
                <Typography sx={{ mt: "10px" }} color="text.secondary" variant="body2">
                    Occurance: 452/1000 | 4.52%
                </Typography>
                <Typography color="text.secondary" variant="body2">
                    Confidence Level 97.87%
                </Typography>
            </Box>

            <Divider variant="middle" />
            <Box sx={{ m: 2 }}>
                <Typography gutterBottom variant="h5">
                    Analytics
                </Typography>

                <AnalyticGraphs />
            </Box>
        </Box >
    );
}
