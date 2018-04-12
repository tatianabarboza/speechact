package decisaocomatosdefala.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import decisaocomatosdefala.atosdefala.AtoDeFala;
import decisaocomatosdefala.nlp.InfinitivoHelper;

public class PontoDecisao {


    public TicketsComMensagens buscarPontosDeDecisao(TicketsComMensagens ticket) {
        TicketsComMensagens ticketPonto = new TicketsComMensagens();
        ticketPonto = buscandoVerbosEmMensagens(ticket);
        return ticketPonto;
    }
    
    
    public static TicketsComMensagens buscandoVerbosEmMensagens(TicketsComMensagens ticket) {
        List<Mensagem> mensagensComVerbos = new ArrayList<>();
        Mensagem msgNovo = null;
        TicketsComMensagens ticketNovo = null;
        ticketNovo = new TicketsComMensagens();
        ticketNovo.setTicketId(ticket.getTicketId());
        for (Mensagem msg : ticket.getMensagens()) {
            List<Verbo> verbos = new ArrayList<>();
            verbos = buscandoVerbos(msg.getMensagem());
            msgNovo = new Mensagem();
            msgNovo.setMensagem(msg.getMensagem());
            msgNovo.setMsgId(msg.getMsgId());
            msgNovo.setVerbos(verbos);
            mensagensComVerbos.add(msgNovo);
        }
        ticketNovo.setMensagens(mensagensComVerbos);
        return ticketNovo;
    }
	
    public static List<TicketsComMensagens> buscandoVerbosEmMensagens(List<TicketsComMensagens> tickets) {
        List<TicketsComMensagens> ticketsComVerbos = new ArrayList<>();
        List<Mensagem> mensagensComVerbos = new ArrayList<>();
        Mensagem msgNovo = null;
        TicketsComMensagens ticketNovo = null;
        
        for (TicketsComMensagens ticket : tickets) {
            ticketNovo = new TicketsComMensagens();
            ticketNovo.setTicketId(ticket.getTicketId());
            
            for (Mensagem msg : ticket.getMensagens()) {
                List<Verbo> verbos = new ArrayList<>();
                
                verbos = buscandoVerbos(msg.getMensagem());
                
                msgNovo = new Mensagem();
                msgNovo.setMensagem(msg.getMensagem());
                msgNovo.setMsgId(msg.getMsgId());
                msgNovo.setVerbos(verbos);
                mensagensComVerbos.add(msgNovo);
            }
            ticketNovo.setMensagens(mensagensComVerbos);
            ticketsComVerbos.add(ticketNovo);
            mensagensComVerbos = new ArrayList<>();
        }
        return ticketsComVerbos;
    }
    
    
    public static List<Verbo> buscandoVerbos(String mensagem) {
        List<Verbo> verbos = new ArrayList<Verbo>();
        String[] assertive = {"affirm", "allege", "assert", "aver", "avow", "claim", "declare", "indicate", "maintain", "propound", "say", "state", "submit"};
        String[] predictive = {"forecast", "predict", "prophesy"};
        String[] retrodictives = {"recount"};
        String[] descriptives = {"call", "categorize", "characterize", "classify", "date", "describe", "diagnose", "evaluate", "grade", "identify", "portray", "rank"};
        String[] ascriptives = {"ascribe", "attribute", "predicate"};
        String[] informatives = {"announce", "apprise", "disclose", "inform", "insist", "notify", "point out", "report", "reveal", "tell"};
        String[] confirmatives = {"appraise", "assess", "bear witness", "certify", "conclude", "confirm", "corroborate", "find", "judge", "substantiate", "testif", "validate", "verif", "vouch for"};
        String[] concessives = {"acknowledge", "admit", "agree", "concede", "concur"};
        String[] retractives = {"abjure", "correct", "deny", "disavow", "disclaim", "disown", "recant", "renounce", "repudiate", "retract", "take back", "withdraw"};
        String[] assentives = {"accept", "assent", "concur"};
        String[] dissentives = {"differ", "disagree", "dissent", "reject"};
        String[] disputatives = {"demur", "dispute", "object", "protest"};
        String[] responsives = {"answer", "reply", "respond", "retort"};
        String[] suggestives = {"conjecture", "guess", "speculate", "suggest"};
        String[] suppositives = {"assume", "hypothesize", "postulate", "stipulate", "suppose", "theorize"};
        String[] requestives = {"ask", "beg", "beseech", "implore", "insist", "invite", "petition", "plead", "pray", "request", "solicit", "summon", "supplicate", "urge"};
        String[] questions = {"inquire", "interrogate", "query", "question", "quiz"};
        String[] requirements = {"bid", "charge", "command", "demand", "dictate", "direct", "enjoin", "instruct", "order", "prescribe", "require"};
        String[] prohibitives = {"forbid", "prohibit", "proscribe", "restrict"};
        String[] permissives = {"allow", "authorize", "bless", "consent to", "dismiss", "excuse", "exempt", "forgive", "grant", "license", "pardon", "release", "sanction"};
        String[] advisories = {"admonish", "advise", "caution", "counsel", "propose", "recommend", "suggest", "urge", "warn"};
        String[] promises = {"promise", "swear", "vow"};
        String[] offers = {"offer"};
        String[] decision = {"close", "complete", "normalized", "solved", "agreed", "choosen", "conclude", "determine", "elect", "end", "establish", "rule", "select", "set", "vote", "detail", "diagnostic", "discrete", "procedure"};
        Verbo verboItem = null;
        String verbo = mensagem.toLowerCase().trim();
        for (String decisao : decision) {
            String decisao1 = decisao;
            decisao = InfinitivoHelper.convertendoParaInfinitivo(decisao);
            if (decisao.equals("")) {
                decisao = decisao1;
            }
            if (verbo.contains(decisao.trim().toLowerCase())) {
                verboItem = new Verbo(decisao1, "decision");
                verbos.add(verboItem);
            }
        }
        for (String assertiva : assertive) {
            String assertiva1 = assertiva;
            assertiva = InfinitivoHelper.convertendoParaInfinitivo(assertiva);
            if (assertiva.equals("")) {
                assertiva = assertiva1;
            }
            if (verbo.contains(assertiva.trim().toLowerCase()) && Objects.equals(AtoDeFala.atoDeFalaAssertive(mensagem, assertive), Boolean.TRUE)) {
                verboItem = new Verbo(assertiva1, "assertive");
                verbos.add(verboItem);
            }
        }
        for (String predictiva : predictive) {
            String predictiva1 = predictiva;
            predictiva = InfinitivoHelper.convertendoParaInfinitivo(predictiva);
            if (predictiva.equals("")) {
                predictiva = predictiva1;
            }
            if (verbo.contains(predictiva.trim().toLowerCase()) && Objects.equals(AtoDeFala.atoDeFalaPredictive(mensagem, predictive), Boolean.TRUE)) {
                verboItem = new Verbo(predictiva1, "predictive");
                verbos.add(verboItem);
            }
        }
        for (String retrodictive : retrodictives) {
            String retrodictive1 = retrodictive;
            retrodictive = InfinitivoHelper.convertendoParaInfinitivo(retrodictive);
            if (retrodictive.equals("")) {
                retrodictive = retrodictive1;
            }
            if (verbo.contains(retrodictive.trim().toLowerCase()) && Objects.equals(AtoDeFala.atoDeFalaRetrodictive(mensagem, retrodictives), Boolean.TRUE)) {
                verboItem = new Verbo(retrodictive1, "retrodictive");
                verbos.add(verboItem);
            }
        }
        for (String descriptive : descriptives) {
            String descriptive1 = descriptive;
            descriptive = InfinitivoHelper.convertendoParaInfinitivo(descriptive);
            if (descriptive.equals("")) {
                descriptive = descriptive1;
            }
            if (verbo.contains(descriptive.trim().toLowerCase()) && Objects.equals(AtoDeFala.atoDeFalaDescriptive(mensagem, descriptives), Boolean.TRUE)) {
                verboItem = new Verbo(descriptive1, "descriptive");
                verbos.add(verboItem);
            }
        }
        for (String ascriptive : ascriptives) {
            String ascriptive1 = ascriptive;
            ascriptive = InfinitivoHelper.convertendoParaInfinitivo(ascriptive);
            if (ascriptive.equals("")) {
                ascriptive = ascriptive1;
            }
            if (verbo.contains(ascriptive.trim().toLowerCase()) && Objects.equals(AtoDeFala.atoDeFalaAscriptives(mensagem, ascriptives), Boolean.TRUE)) {
                verboItem = new Verbo(ascriptive1, "ascriptive");
                verbos.add(verboItem);
            }
        }
        for (String informative : informatives) {
            String informative1 = informative;
            informative = InfinitivoHelper.convertendoParaInfinitivo(informative);
            if (informative.equals("")) {
                informative = informative1;
            }
            if (verbo.contains(informative.trim().toLowerCase()) && Objects.equals(AtoDeFala.atoDeFalaInformative(mensagem, informatives), Boolean.TRUE)) {
                verboItem = new Verbo(informative1, "informative");
                verbos.add(verboItem);
            }
        }
        for (String confirmative : confirmatives) {
            String confirmative1 = confirmative;
            confirmative = InfinitivoHelper.convertendoParaInfinitivo(confirmative);
            if (confirmative.equals("")) {
                confirmative = confirmative1;
            }
            if (verbo.contains(confirmative.trim().toLowerCase()) && Objects.equals(AtoDeFala.atoDeFalaConfirmative(mensagem, confirmatives), Boolean.TRUE)) {
                verboItem = new Verbo(confirmative1, "confirmative");
                verbos.add(verboItem);
            }
        }
        for (String concessive : concessives) {
            String concessive1 = concessive;
            concessive = InfinitivoHelper.convertendoParaInfinitivo(concessive);
            if (concessive.equals("")) {
                concessive = concessive1;
            }
            if (verbo.contains(concessive.trim().toLowerCase()) && Objects.equals(AtoDeFala.atoDeFalaConcessive(mensagem, concessives), Boolean.TRUE)) {
                verboItem = new Verbo(concessive1, "concessive");
                verbos.add(verboItem);
            }
        }
        for (String retractive : retractives) {
            String retractive1 = retractive;
            retractive = InfinitivoHelper.convertendoParaInfinitivo(retractive);
            if (retractive.equals("")) {
                retractive = retractive1;
            }
            if (verbo.contains(retractive.trim().toLowerCase()) && Objects.equals(AtoDeFala.atoDeFalaRetractive(mensagem, retractives), Boolean.TRUE)) {
                verboItem = new Verbo(retractive1, "retractive");
                verbos.add(verboItem);
            }
        }
        for (String assentive : assentives) {
            String assentive1 = assentive;
            assentive = InfinitivoHelper.convertendoParaInfinitivo(assentive);
            if (assentive.equals("")) {
                assentive = assentive1;
            }
            if (verbo.contains(assentive.trim().toLowerCase()) && Objects.equals(AtoDeFala.atoDeFalaAssentive(mensagem, assentives), Boolean.TRUE)) {
                verboItem = new Verbo(assentive1, "assentive");
                verbos.add(verboItem);
            }
        }
        for (String dissentive : dissentives) {
            String dissentive1 = dissentive;
            dissentive = InfinitivoHelper.convertendoParaInfinitivo(dissentive);
            if (dissentive.equals("")) {
                dissentive = dissentive1;
            }
            if (verbo.contains(dissentive.trim().toLowerCase()) && Objects.equals(AtoDeFala.atoDeFalaDissentive(mensagem, dissentives), Boolean.TRUE)) {
                verboItem = new Verbo(dissentive1, "dissentive");
                verbos.add(verboItem);
            }
        }
        for (String disputative : disputatives) {
            String disputative1 = disputative;
            disputative = InfinitivoHelper.convertendoParaInfinitivo(disputative);
            if (disputative.equals("")) {
                disputative = disputative1;
            }
            if (verbo.contains(disputative.trim().toLowerCase()) && Objects.equals(AtoDeFala.atoDeFalaDisputative(mensagem, disputatives), Boolean.TRUE)) {
                verboItem = new Verbo(disputative1, "disputative");
                verbos.add(verboItem);
            }
        }
        for (String responsive : responsives) {
            String responsive1 = responsive;
            responsive = InfinitivoHelper.convertendoParaInfinitivo(responsive);
            if (responsive.equals("")) {
                responsive = responsive1;
            }
            if (verbo.contains(responsive.trim().toLowerCase()) && Objects.equals(AtoDeFala.atoDeFalaResponsives(mensagem, responsives), Boolean.TRUE)) {
                verboItem = new Verbo(responsive1, "responsive");
                verbos.add(verboItem);
            }
        }
        for (String suggestive : suggestives) {
            String suggestive1 = suggestive;
            suggestive = InfinitivoHelper.convertendoParaInfinitivo(suggestive);
            if (suggestive.equals("")) {
                suggestive = suggestive1;
            }
            if (verbo.contains(suggestive.trim().toLowerCase()) && Objects.equals(AtoDeFala.atoDeFalaSuggestives(mensagem, suggestives), Boolean.TRUE)) {
                verboItem = new Verbo(suggestive1, "suggestive");
                verbos.add(verboItem);
            }
        }
        for (String suppositive : suppositives) {
            String suppositive1 = suppositive;
            suppositive = InfinitivoHelper.convertendoParaInfinitivo(suppositive);
            if (suppositive.equals("")) {
                suppositive = suppositive1;
            }
            if (verbo.contains(suppositive.trim().toLowerCase()) && Objects.equals(AtoDeFala.atoDeFalaSuppositive(mensagem, suppositives), Boolean.TRUE)) {
                verboItem = new Verbo(suppositive1, "suppositive");
                verbos.add(verboItem);
            }
        }
        for (String requestive : requestives) {
            String requestive1 = requestive;
            requestive = InfinitivoHelper.convertendoParaInfinitivo(requestive);
            if (requestive.equals("")) {
                requestive = requestive1;
            }
            if (verbo.contains(requestive.trim().toLowerCase()) && Objects.equals(AtoDeFala.atoDeFalaRequestive(mensagem, requestives), Boolean.TRUE)) {
                verboItem = new Verbo(requestive1, "requestive");
                verbos.add(verboItem);
            }
        }
        for (String question : questions) {
            String question1 = question;
            question = InfinitivoHelper.convertendoParaInfinitivo(question);
            if (question.equals("")) {
                question = question1;
            }
            if (verbo.contains(question.trim().toLowerCase()) && Objects.equals(AtoDeFala.atoDeFalaQuestions(mensagem, questions), Boolean.TRUE)) {
                verboItem = new Verbo(question1, "question");
                verbos.add(verboItem);
            }
        }
        for (String requirement : requirements) {
            String requirement1 = requirement;
            requirement = InfinitivoHelper.convertendoParaInfinitivo(requirement);
            if (requirement.equals("")) {
                requirement = requirement1;
            }
            if (verbo.contains(requirement.trim().toLowerCase()) && Objects.equals(AtoDeFala.atoDeFalaRequirements(mensagem, requirements), Boolean.TRUE)) {
                verboItem = new Verbo(requirement1, "requirement");
                verbos.add(verboItem);
            }
        }
        for (String prohibitive : prohibitives) {
            String prohibitive1 = prohibitive;
            prohibitive = InfinitivoHelper.convertendoParaInfinitivo(prohibitive);
            if (prohibitive.equals("")) {
                prohibitive = prohibitive1;
            }
            if (verbo.contains(prohibitive.trim().toLowerCase()) && Objects.equals(AtoDeFala.atoDeFalaProhibitive(mensagem, prohibitives), Boolean.TRUE)) {
                verboItem = new Verbo(prohibitive1, "prohibitive");
                verbos.add(verboItem);
            }
        }
        for (String permissive : permissives) {
            String permissive1 = permissive;
            permissive = InfinitivoHelper.convertendoParaInfinitivo(permissive);
            if (permissive.equals("")) {
                permissive = permissive1;
            }
            if (verbo.contains(permissive.trim().toLowerCase()) && Objects.equals(AtoDeFala.atoDeFalaPermissive(mensagem, permissives), Boolean.TRUE)) {
                verboItem = new Verbo(permissive1, "permissive");
                verbos.add(verboItem);
            }
        }
        for (String advisorie : advisories) {
            String advisorie1 = advisorie;
            advisorie = InfinitivoHelper.convertendoParaInfinitivo(advisorie);
            if (advisorie.equals("")) {
                advisorie = advisorie1;
            }
            if (verbo.contains(advisorie.trim().toLowerCase()) && Objects.equals(AtoDeFala.atoDeFalaAdvisories(mensagem, advisories), Boolean.TRUE)) {
                verboItem = new Verbo(advisorie1, "advisories");
                verbos.add(verboItem);
            }
        }
        for (String promise : promises) {
            String promise1 = promise;
            promise = InfinitivoHelper.convertendoParaInfinitivo(promise);
            if (promise.equals("")) {
                promise = promise1;
            }
            if (verbo.contains(promise.trim().toLowerCase()) && Objects.equals(AtoDeFala.atoDeFalaPromisse(mensagem, promises), Boolean.TRUE)) {
                verboItem = new Verbo(promise1, "promises");
                verbos.add(verboItem);
            }
        }
        for (String offer : offers) {
            String offer1 = offer;
            offer =  InfinitivoHelper.convertendoParaInfinitivo(offer);
            if (offer.equals("")) {
                offer = offer1;
            }
            if (verbo.contains(offer.trim().toLowerCase()) && Objects.equals(AtoDeFala.atoDeFalaOffer(mensagem, offers), Boolean.TRUE)) {
                verboItem = new Verbo(offer1, "offers");
                verbos.add(verboItem);
            }
        }

        return verbos;
    }
    
}
