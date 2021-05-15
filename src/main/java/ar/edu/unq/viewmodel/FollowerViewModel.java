package ar.edu.unq.viewmodel;

public class FollowerViewModel {
    private Integer idTo;

	private Integer idFrom;

    public Integer getIdTo(){
        return this.idTo;
    }

    public Integer getIdFrom(){
        return this.idFrom;
    }

    public void setIdTo(Integer idTo){
        this.idTo = idTo;
    }

    public void setIdFrom(Integer idFrom){
        this.idFrom = idFrom;
    }
}
