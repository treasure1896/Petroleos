package Modelos;

/**
 *
 * @author Miguel
 */
public class Eaton {
    double P1, P2, Tprom, Yg, Yw,qL, RGL, d, YAPI, Wcut, Bw,R,Go,Gw,uo,uw,NLB;

    public Eaton(double P1, double P2, double Tprom, double Yg, double Yw, double qL, double RGL, double d, double YAPI, double Wcut, double Bw, double R,double Go,double Gw,double uo,double uw, double NLB) {
        this.P1 = P1;
        this.P2 = P2;
        this.Tprom = Tprom;
        this.Yg = Yg;
        this.Yw = Yw;
        this.qL = qL;
        this.RGL = RGL;
        this.d = d;
        this.YAPI = YAPI;
        this.Wcut = Wcut;  
        this.Bw = Bw;
        this.R = R;
        this.Go = Go;
        this.Gw = Gw;
        this.uo = uo;
        this.uw = uw;
        this.NLB = NLB;
    }   
    public double getTprom()
    {
        return Tprom;
    }       
    public double getPprom()
    {
        return (P1+P2)/2;
    }   
    public double getPpc()
    {
        return 756.8-(131*Yg)-(3.6*Math.pow(Yg,2));
    }
    public double getPpr()
    {
        return getPprom()/getPpc();
    }
    public double getTpc()
    {
        return 169.2-(349.5*Yg)-(74*Math.pow(Yg,2))+460;
    }
    public double getTpr()//Revisar error H15 o D19            
    {
        return (Tprom+460)/getTpc();
    }   
    
    public double getZprom()
    {
        double A1 = 0.31506;
        double A2 = -1.0467;
        double A3 = -0.5783;
        double A4 = 0.5353;
        double A5 = -0.6123;
        double A6 = -0.6123;
        double A7 = 0.68157;
        double A8 = 0.68446;
        double Zsup = 0.8;
        double pr = (0.27*getPpr())/(Zsup*getTpr());                
        double Z = 1+((A1+(A2/getTpr())+(A3/(Math.pow(getTpr(),3))))*pr)+((A4+(A5/getTpr()))*Math.pow(pr,2))+((A5*A6*(Math.pow(pr,5)))/getTpr())+(((A7*Math.pow(pr,2))/Math.pow(getTpr(),3))*(1+(A8*Math.pow(pr,2)))*Math.exp((-1)*A8*Math.pow(pr,2)));
        
        for (int i = 0; i < 8; i++) 
        {
            Zsup = Z;
            pr = (0.27*getPpr())/(Zsup*getTpr());
            Z = 1+((A1+(A2/getTpr())+(A3/(Math.pow(getTpr(),3))))*pr)+((A4+(A5/getTpr()))*Math.pow(pr,2))+((A5*A6*(Math.pow(pr,5)))/getTpr())+(((A7*Math.pow(pr,2))/Math.pow(getTpr(),3))*(1+(A8*Math.pow(pr,2)))*Math.exp((-1)*A8*Math.pow(pr,2)));        
        }        
        return Z;
    }
    public double getPgprom()
    {
        double H15 = 0;
        return Yg*0.0764*(520.0/(H15+460))*(getPprom()/14.7)*(1.0/getZprom());
    }
    public double getYo()
    {
        return 141.5/(YAPI+131.5);
    }
    public double getRsprom()
    {
        return Yg*(Math.pow((((getPprom()/18.2)+1.4)*(Math.pow(10,((0.0125*YAPI)-(0.00091*Tprom))))),1.2048));
    }
    public double getF()
    {
        return (getRsprom()*Math.pow((Yg/getYo()),0.5))+(1.25*Tprom);
    }
    public double getBprom()
    {
        return 0.9759+0.00012*Math.pow(getF(),1.2);
    }
    public double getpoprom()
    {
        return (350*getYo()+0.0764*getRsprom()*Yg)/(5.6146*getBprom());
    }
    public double getWOR()
    {
        return Wcut/(1-Wcut);
    }
    public double getPwprom()
    {
        return (350*Yw)/(5.615*Bw);
    }
    public double getpLprom()
    {
        double r = (getpoprom()/(1+getWOR()))+((getPwprom()*getWOR())/(1+getWOR()));
        return r;
    }
    public double getqw()
    {
        return (getWOR()*qL)/(1+getWOR());
    }
    public double getqo()
    {
        return qL-getqw();
    }
    public double getWL()
    {
        return (5.6146*qL*getpLprom())/86400.0;
    }
     
    public double getqg()
    {
        return qL*RGL;
    }
    public double getWg()
    {
        return (0.0764*Yg*getqg())/86400.0;
    }
    public double getWm()
    {
        return getWL()+getWg();
    } 
    
   
    
    public double getqg1()
    {    
        return qL*(R-getRsprom())*(14.7/P1)*((Tprom+460)/520)*getZprom();
    }    
    public double getAp()
    {
        return (Math.PI*Math.pow(d, 2))/4.0;
    }
    public double getVsg1()
    {
        return (getqg1()*144)/(86400*getAp());
    }
    public double getVsL1()
    {
        return (5.6146*qL*144)/(86400.0*getAp());
    }
    public double getVm1()
    {
        return getVsL1()+getVsg1();
    }
    public double getGL()
    {
        return (Go/(1+getWOR()))+((Gw*getWOR())/(1+getWOR()));
    }
    public double getNLV1()
    {
        return 1.938*getVsL1()*Math.pow((getpLprom()/getGL()),0.25);
    }
    public double getNgV1()
    {
        return 1.938*getVsg1()*Math.pow((getpLprom()/getGL()),0.25);
    }
    public double getNd()
    {
        return (120.872*d*Math.pow((getpLprom()/getGL()),0.5))/12;
    }
    public double getuL()
    {
        return (uo/(1+getWOR()))+((uw*getWOR())/(1+getWOR()));
    }
    public double getNL()
    {
        return 0.15726*getuL()*Math.pow((1/(getpLprom()*Math.pow(getGL(),3))),0.25);
    }
    public double getPPCS()
    {
        return P1/14.7;
    }
    public double getB1()
    {
        return (Math.pow(getNLV1(),0.575)*Math.pow(getPPCS(),0.05)*Math.pow((getNL()/NLB),0.1))/(getNgV1()*Math.pow(getNd(),0.0277));
    }
    
    
    
    
    public double getHL1()//Grafica
    {
        return 0.3;
    }
    public double get1HL1()
    {
        return 1-getHL1();
    }
    public double getqgprom2()
    {
        double H15 = 0;
        return getqg()*(14.7/P2)*((H15+460)/520)*getZprom();
    }
    public double getVsg2()
    {
        return (getqgprom2()*144)/(86400*getAp());
    }
    /*public double getVsL2()
    {
        return (5.6146*qL*144)/(86400.0*getAp());
    }revisar igual Vsl2 H92*/
    public double getVm2()//Revisar VsL = VsL2
    {
        return getVsL1()+getVsg2();
    }
    public double getHL2()//Grafica
    {
        return 0.27;
    }
    public double get1HL2()
    {
        return 0.73;
    }
    public double getVL1()
    {
        return getVsL1()*(1.0/getHL1());
    }
    public double getVL2()//Vsl=Vsl2?
    {
        return getVsL1()*(1.0/getHL2());
    }
    public double getDVL()
    {
        return getVL2()-getVL1();
    }
    public double getVg1()
    {
        return getVsg1()*(1/get1HL1());
    }
    public double getVg2()
    {
        return getVsg2()*(1/get1HL2());
    }
    public double getDVg()
    {
        return getVg2()-getVg1();
    }
    public double getGR()
    {
        return getWg()/getWm();
    }
    public double getLR()
    {
        return getWL()/getWm();
    }
    public double getLRcalculado()
    {
        return Math.pow(getLR(), 0.1);
    }
    public double getVmprom()
    {
        return (getVm1()+getVm2())/2.0;
    }
    public double getLRleido()//grafica
    {
        return 0.022;
    }
    public double getf()
    {
        return getLRleido()/getLRcalculado();
    }
    public double getDL()
    {
        double p1 = getVg1();
        double p2 = P2;
        System.out.println("p1 "+p1);
        System.out.println("p2 "+p2);
        return ((2*32.2*d)/(12*getWm()*Math.pow(getVmprom(),2)*getf()))*((144*(P1-P2)*((getWL()/getpLprom())+(getWg()/getPgprom())))-(((getWL()*Math.pow(getDVL(),2.0))+(getWg()*Math.pow(getDVg(),2)))/(2.0*32.2)));
    }  
}
