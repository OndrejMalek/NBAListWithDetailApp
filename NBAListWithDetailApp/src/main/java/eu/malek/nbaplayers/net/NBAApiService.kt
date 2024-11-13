package eu.malek.nbaplayers.net

class NBAApiService(val service: NBAApi = NBAApi.createService()) : NBAApi by service