import * as React from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import { CardActionArea } from '@mui/material';

type AppProps = {
    id: string
}

export default function ActionAreaCard({ id }: AppProps) {
    return (
        <Card sx={{ mb: 1.5 }}>
            <CardActionArea>
                <CardContent>
                    <Typography gutterBottom variant="h5" component="div">
                        {id}
                    </Typography>
                    <Typography variant="body2" color="text.primary">
                        "What can I help you with today?"
                    </Typography>
                    <Typography variant="body2" color="text.secondary" sx={{ display: "inline" }}>
                        Occurance: 452/1000 | 4.52%
                    </Typography>
                    <Typography variant="body2" color="text.secondary" sx={{ display: "inline", flexDirection: "column-reverse" }}>
                        Confidence Level 97.87%
                    </Typography>


                </CardContent>
            </CardActionArea>
        </Card>
    );
}
