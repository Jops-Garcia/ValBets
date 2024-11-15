// SPDX-License-Identifier: MIT

pragma solidity ^0.8.0;

contract Betting {
    address public owner;
    uint public teamAScore;
    uint public teamBScore;

    mapping(address => uint) public betsOnTeamA;
    mapping(address => uint) public betsOnTeamB;
    address[] public bettorsOnTeamA;
    address[] public bettorsOnTeamB;
    uint public totalBetOnTeamA;
    uint public totalBetOnTeamB;

    constructor() {
        owner = msg.sender;
    }

    function placeBetOnTeamA() public payable {
        require(msg.value > 0, "Bet amount must be greater than zero");

        // Se o apostador não tiver apostado antes, adicionar ao array
        if (betsOnTeamA[msg.sender] == 0) {
            bettorsOnTeamA.push(msg.sender);
        }

        betsOnTeamA[msg.sender] += msg.value;
        totalBetOnTeamA += msg.value;
    }

    function placeBetOnTeamB() public payable {
        require(msg.value > 0, "Bet amount must be greater than zero");

        // Se o apostador não tiver apostado antes, adicionar ao array
        if (betsOnTeamB[msg.sender] == 0) {
            bettorsOnTeamB.push(msg.sender);
        }

        betsOnTeamB[msg.sender] += msg.value;
        totalBetOnTeamB += msg.value;
    }

    function declareWinner(uint _teamAScore, uint _teamBScore) public {
        require(msg.sender == owner, "Only owner can declare the winner");
        teamAScore = _teamAScore;
        teamBScore = _teamBScore;

        if (teamAScore > teamBScore) {
            distributeRewards(bettorsOnTeamA, betsOnTeamA, totalBetOnTeamA, totalBetOnTeamB);
        } else if (teamBScore > teamAScore) {
            distributeRewards(bettorsOnTeamB, betsOnTeamB, totalBetOnTeamB, totalBetOnTeamA);
        }
    }

    function distributeRewards(address[] storage bettors, mapping(address => uint) storage winners, uint winningPool, uint losingPool) internal {
        for (uint i = 0; i < bettors.length; i++) {
            address bettor = bettors[i];
            uint betAmount = winners[bettor];
            uint reward = betAmount + (betAmount * losingPool / winningPool);
            payable(bettor).transfer(reward);
        }
    }
}
