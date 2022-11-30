/***Analytics.tsx***/
export type Message = {
  message: string,
  traceType: string
}

export type RawSuggestion = {
  chatBotMessage: Message[],
  userMessage: Message[],
  stepSuggestion: string[],
  confidenceScore: number[]
}

export type FormattedSuggestion = {
  chatBotMessage: string,
  userMessage: string,
  topSuggestion: string,
  topConfidence: number,
  stepSuggestions: string[],
  confidenceScores: number[],
  data: ChartData[],
  example: boolean
}

export type ChartData = {
  title: string, 
  ranges: number[],
  Target: number[],
  Confidence: number[], 
}

/***SuggestionCard.tsx***/
export type SuggestionCardInput = {
  suggestion: FormattedSuggestion,
  selectSuggestion: any
}