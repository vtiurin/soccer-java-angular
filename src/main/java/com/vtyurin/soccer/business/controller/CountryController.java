package com.vtyurin.soccer.business.controller;

import com.vtyurin.soccer.business.entity.Club;
import com.vtyurin.soccer.business.entity.Country;
import com.vtyurin.soccer.business.entity.Player;
import com.vtyurin.soccer.business.service.CountryService;
import java.util.ArrayList;
import java.util.Collection;
import javax.inject.Inject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path(Link.COUNTRIES)
public class CountryController {
    
    @Inject
    CountryService countryService;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON + "; " +
              MediaType.CHARSET_PARAMETER + "=UTF-8")
    public CollectionResource countryList(@Context UriInfo info) {
        Collection<Country> countries = countryService.getCountries();
        Collection items = new ArrayList(countries.size());
        
        for (Country country : countries) {
            items.add(new CountryResource(info, country));
        }
        
        return new CollectionResource(info, items, Link.COUNTRIES);
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON + "; " +
              MediaType.CHARSET_PARAMETER + "=UTF-8")
    public CountryResource getCountry(@Context UriInfo info, @PathParam("id") long id) {
        Country country = countryService.getById(id);
        return new CountryResource(info, country);
    }
    
    @GET
    @Path("/{id}/players")
    @Produces(MediaType.APPLICATION_JSON + "; " +
              MediaType.CHARSET_PARAMETER + "=UTF-8")
    public CollectionResource getPlayers(@Context UriInfo info, @PathParam("id") long id) {
        Country country = countryService.getById(id);
        Collection<Player> players = countryService.getPlayers(country);
        Collection items = new ArrayList(players.size());
        
        for (Player player : players) {
            items.add(new PlayerResource(info, player));
        }
        
        return new CollectionResource(info, items, info.getPath());
    }
    
    @GET
    @Path("/{id}/clubs")
    @Produces(MediaType.APPLICATION_JSON + "; " +
              MediaType.CHARSET_PARAMETER + "=UTF-8")
    public CollectionResource getClubs(@Context UriInfo info, @PathParam("id") long id) {
        Country country = countryService.getById(id);
        Collection<Club> clubs = countryService.getClubs(country);
        Collection items = new ArrayList(clubs.size());
        
        for (Club club : clubs) {
            items.add(new ClubResource(info, club));
        }
        
        return new CollectionResource(info, items, info.getPath());
    }
}