package com.abhisheksamuely.ipl.data;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

import com.abhisheksamuely.ipl.models.Match;

public class MatchDataProcessor implements ItemProcessor<MatchInput, Match> {

	  private static final Logger log = LoggerFactory.getLogger(MatchDataProcessor.class);

	  @Override
	  public Match process(final MatchInput matchInput) throws Exception {
		Match match = new Match();
	    match.setId(Long.parseLong(matchInput.getId()));
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    Date date = sdf.parse(matchInput.getDate());
	    match.setDate(date.getTime());
	    match.setPlayerOfMatch(matchInput.getPlayer_of_match());
	    match.setVenue(matchInput.getVenue());
	    //set toss winner and choose to bat as team 1
	    String firstInningsTeam, secondInningsTeam;
	    if(matchInput.getToss_decision().equalsIgnoreCase("bat")) {
	    	firstInningsTeam = matchInput.getToss_winner();
	    	secondInningsTeam = matchInput.getToss_winner().equals(matchInput.getTeam1()) ? matchInput.getTeam2() : matchInput.getTeam1();
	    } else {
	    	secondInningsTeam = matchInput.getToss_winner();
	    	firstInningsTeam = matchInput.getToss_winner().equals(matchInput.getTeam1()) ? matchInput.getTeam2() : matchInput.getTeam1();
	    }
	    match.setTeam1(firstInningsTeam);
	    match.setTeam2(secondInningsTeam);
	    match.setTossWinner(matchInput.getToss_winner());
	    match.setTossDecision(matchInput.getToss_decision());
	    match.setMatchWinner(matchInput.getWinner());
	    match.setResult(matchInput.getResult());
	    match.setResultMargin(matchInput.getResult_margin());
	    match.setUmpire1(matchInput.getUmpire1());
	    match.setUmpire2(matchInput.getUmpire2());
	    
	    log.info("Converting (" + matchInput + ") into (" + match + ")");

	    return match;
	  }
}