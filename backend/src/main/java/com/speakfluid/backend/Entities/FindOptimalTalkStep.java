java.import.util.*;
//suggestion class (noun)
public class FindOptimalTalkStep {
    private TextStep text;
    private ImageStep image;
    private CardStep card;
    private CarouselStep carousel;
    private ChoiceStep choice;
    private ButtonStep button;
    private CaptureStep capture;

    public FindOptimalTalkStep(Step text, Step card, Step carousel, Step choice,
                               Step button, Step capture) {
        this.text = text;
        this.image = image;
        this.card = card;
        this.carousel = carousel;
        this.choice = choice;
        this.button = button;
        this.capture = capture;
        //have a class just constructing these objects// have usecase interactor responsible for that
    }

    //in useCaseInteractor, have a loop iterating over FindOptimalTalkStep and keep track of confidence score
    //and suggested talk step each time

    public int calculateSuggestTextStep(singleResponse response){
        //return integer of accumulation of how many methods have satisfied their conditions
        text.isStatement(response);
        text.isFirstLastSentence(reponse);
    }
    //have Step parent class for all TalkSteps so this is less coupled and more general
    public int calculateSuggestImageStep(singleResponse response) {
        image.isImageKeyword(response);
    }
    public int calculateSuggestCardStep(singleResponse response){
        card.isImageKeyword(response);
        card.matchCardKeywords(response); ////can implment return number of keywords
        card.isFastResponse(response);
        card.isShortAnswer(response);

    }
    public double confidenceScore(Step talkStep) {
        //calculate confidence score of button and image to pass into carousel calculation
        //perhaps make this a separate class
    }
    public int calculateSuggestCarousel(singleResponse response, int confidenceButton,
                                        int confidenceImage) {
        carousel.isButtonAndCard(confidenceButton, confidenceImage);

    }
    public int calculateSuggestChoice(singleResponse response) {
        choice.matchChoiceKeywords(response);
    }
    public int calculateSuggestButton(singleResponse response) {
        button.matchButtonKeywords(response);
        button.isShortAnswer(response);
        button.isFastResponse(response);
    }
    public int calculateSuggestCapture(singleResponse response) {
        capture.matchCaptureKeywords(response);
    }


}